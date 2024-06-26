package nl.sogyo.modelr.services

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import nl.sogyo.modelr.*
import nl.sogyo.modelr.entities.*
import nl.sogyo.modelr.entities.Simulation
import nl.sogyo.modelr.models.*
import nl.sogyo.modelr.services.ApiResult.Failure
import nl.sogyo.modelr.services.ApiResult.Success
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SimulationService(
    private val simulationRepository: SimulationRepository,
    private val batchCultivationRepository: BatchCultivationRepository,
    private val microorganismRepository: MicroorganismRepository,
    private val reactorRepository: ReactorRepository,
    private val impellerRepository: ImpellerRepository,
    private val costFactorRepository: CostFactorRepository,
    private val batchRequestRepository: BatchRequestRepository,
    private val cultivationSettingsRepository: CultivationSettingsRepository,
    private val reactorSettingsRepository: ReactorSettingsRepository,
    private val centrifugeRepository: CentrifugeRepository,
    private val centrifugationRepository: CentrifugationRepository,
    private val centrifugationSettingsRepository: CentrifugationSettingsRepository,
    private val centrifugationRequestRepository: CentrifugationRequestRepository,
    private val centrifugePropertiesRepository: CentrifugePropertiesRepository
) {

    @Transactional
    fun runNewSimulation(request: SimulationRequestDTO, factory: ISimulationFactory): ApiResult<Any> {
        try {
            val objectMapper = jacksonObjectMapper()
            objectMapper.registerModule(JavaTimeModule())

            val simulationId = saveNewSimulation(request)

            val operations = request.order

            val settings = objectMapper.writeValueAsString(simulationRepository.findById(simulationId).get())

            val simulation: ISimulation = factory.createNewSimulation(operations, settings)

            val result = objectMapper.readValue<SimulationResultDTO>(objectMapper.writeValueAsString(simulation.runSimulation()))

            saveResults(result, objectMapper, simulationId)

            return Success(simulationId)
        }
        catch (e: IllegalArgumentException){
            return Failure(ErrorCode.OPERATION_NOT_FOUND, "${e.message}")
        }
        catch (e: EmptyStringException) {
            return Failure(ErrorCode.EMPTY_STRING_INPUT, "${e.message}")
        }
        catch (e: Exception) {
            return Failure(ErrorCode.GENERAL_ERROR, "An unexpected error occurred (${e.message}!")
        }
    }

    fun saveNewSimulation(request: SimulationRequestDTO): Long {
        val operations = saveUnitOperations(request, listOf(0, 0), emptyList())

        val simulation = createSimulation(operations)

        return simulationRepository.save(simulation).id!!
    }

    private fun createSimulation(operations: List<Operation>): Simulation {
        val batch: MutableList<BatchCultivation?> = mutableListOf()
        val centrifuge: MutableList<Centrifugation?> = mutableListOf()
        operations.map {operation ->
            when (operation.type) {
                "batch-cultivation" -> { batch.add(batchCultivationRepository.findById(operation.id).get()) }
                "centrifugation" -> { centrifuge.add(centrifugationRepository.findById(operation.id).get()) }
                else -> throw IllegalArgumentException("Unexpected operation type $operation")
            }
        }
        return Simulation(batchCultivation = batch, centrifugation = centrifuge)
    }

    private fun saveUnitOperations(request: SimulationRequestDTO, savedOperations: List<Int>, accumulator: List<Operation>): List<Operation> {
        return if (request.order.size == savedOperations.sum()) {
            accumulator
        } else {
            val nextOperation = saveUnitOperation(request.order[savedOperations.sum()], request, savedOperations.sum(), savedOperations)
            val newSavedOperations = updateSavedOperations(nextOperation, savedOperations)
            saveUnitOperations(request, newSavedOperations, accumulator + nextOperation)
        }
    }

    private fun saveUnitOperation(operation: String, request: SimulationRequestDTO, position: Int, savedOperations: List<Int>): Operation {
        return when (operation) {
            "batch-cultivation" -> saveBatchCultivation(request.batchCultivation[savedOperations[0]]!!, position)
            "centrifugation" -> saveCentrifugation(request.centrifugation[savedOperations[1]]!!, position)
            else -> throw IllegalArgumentException("Unit operation not found ($operation)")
        }
    }

    private fun updateSavedOperations(nextOperation: Operation, savedOperations: List<Int>): List<Int> {
        return when (nextOperation.type) {
            "batch-cultivation" -> listOf(savedOperations[0] + 1, savedOperations[1])
            "centrifugation" -> listOf(savedOperations[0], savedOperations[1] + 1)
            else -> throw IllegalArgumentException("Unexpected nextOperation type ${nextOperation.type}")
        }
    }

    private fun saveBatchCultivation(request: BatchCultivationRequestDTO, position: Int): Operation {
        val requestId = saveBatchRequest(request)

        val savedRequest = batchRequestRepository.findById(requestId)

        val microorganism = microorganismRepository.findMicroorganismsByName(request.cultivationSettings.microorganism)

        val reactor = reactorRepository.findReactorByName(request.reactorSettings.reactorType)

        val impeller = impellerRepository.findImpellerByType(request.reactorSettings.impellerType)

        val costFactor = costFactorRepository.findFirstByOrderByDateDesc()

        val batchCultivation = BatchCultivation(position, savedRequest.get(), null, costFactor!!, microorganism!!, reactor!!, impeller!!)

        return Operation("batch-cultivation", batchCultivationRepository.save(batchCultivation).id!!)
    }

    private fun saveBatchRequest(request: BatchCultivationRequestDTO): Long {
        val operationType = request.operationType

        val cultivationSettingsId = saveCultivationSettings(request.cultivationSettings)

        val reactorSettingsId = saveReactorSettings(request.reactorSettings)

        val cultivationSettings = cultivationSettingsRepository.findById(cultivationSettingsId)

        val reactorSettings = reactorSettingsRepository.findById(reactorSettingsId)

        return batchRequestRepository.save(BatchRequest(operationType, reactorSettings.get(), cultivationSettings.get())).id!!
    }

    private fun saveCultivationSettings(request: CultivationSettingsDTO): Long {
        if (request.microorganism == "") {
            throw EmptyStringException("Microorganism input is empty string!")
        } else {
            return cultivationSettingsRepository.save(
                CultivationSettings(
                    request.microorganism,
                    request.accuracy,
                    request.initialSugarConcentration,
                    request.initialCellDensity,
                    request.maxGrowthRate,
                    request.maintenance,
                    request.yield
                )
            ).id!!
        }
    }

    private fun saveReactorSettings(request: ReactorSettingsDTO): Long {
        if (request.reactorType == "" || request.impellerType == "") {
            throw EmptyStringException("Request reactor settings contains empty string (ReactorType or ImpellerType)")
        } else {
            return reactorSettingsRepository.save(
                ReactorSettings(
                    request.reactorType,
                    request.nominalVolume,
                    request.workingVolume,
                    request.height,
                    request.width,
                    request.impellerType,
                    request.numberOfImpellers,
                    request.agitatorSpeed
                )
            ).id!!
        }
    }

    private fun saveCentrifugation(request: CentrifugationRequestDTO, position: Int): Operation {
        val requestId = saveCentrifugationRequest(request)

        val savedRequest = centrifugationRequestRepository.findById(requestId)

        val centrifuge = centrifugeRepository.findCentrifugeByName(request.centrifugeProperties.centrifugeType)

        val costFactor = costFactorRepository.findFirstByOrderByDateDesc()

        val centrifugation = Centrifugation(position, savedRequest.get(),null, costFactor!!, centrifuge!!)

        return Operation("centrifugation", centrifugationRepository.save(centrifugation).id!!)
    }

    private fun saveCentrifugationRequest(request: CentrifugationRequestDTO): Long {
        val operationType = request.operationType

        val centrifugationSettingsId = saveCentrifugationSettings(request.centrifugationSettings)

        val centrifugePropertiesId = saveCentrifugeProperties(request.centrifugeProperties)

        val centrifugationSettings = centrifugationSettingsRepository.findById(centrifugationSettingsId)

        val centrifugeProperties = centrifugePropertiesRepository.findById(centrifugePropertiesId)

        return centrifugationRequestRepository.save(CentrifugationRequest(operationType, centrifugationSettings.get(), centrifugeProperties.get())).id!!
    }

    private fun saveCentrifugationSettings(request: CentrifugationSettingsDTO): Long {
        return centrifugationSettingsRepository.save(
            CentrifugationSettings(
                request.frequencyOfRotation,
                request.liquidFlowRate,
                request.liquidVolume,
                )
        ).id!!
    }

    private fun saveCentrifugeProperties(request: CentrifugePropertiesDTO): Long {
        if (request.centrifugeType == "") {
            throw EmptyStringException("Centrifuge type input is empty string!")
        } else {
            return centrifugePropertiesRepository.save(
                CentrifugeProperties(
                    request.centrifugeType,
                    request.outerRadius,
                    request.innerRadius,
                    request.numberOfDisks,
                    request.diskAngle,
                    request.motorPower
                )
            ).id!!
        }
    }

    private fun saveResults(result: SimulationResultDTO, objectMapper: ObjectMapper, simulationId: Long) {
        val simulation = simulationRepository.findById(simulationId).get()

        val resultStrings = result.output.map { operationResultDTO -> objectMapper.writeValueAsString(operationResultDTO) }

        saveBatchCultivations(simulation, resultStrings)
        saveCentrifugations(simulation, resultStrings)
    }

    private fun saveBatchCultivations(simulation: Simulation, resultStrings: List<String>) {
        simulation.batchCultivation.forEach {
            batch -> if (batch != null) {
                val position = batch.position
                batchCultivationRepository.setResultForBatchCultivation(batch.id!!, resultStrings[position])
            }
        }
    }

    private fun saveCentrifugations(simulation: Simulation, resultStrings: List<String>) {
        simulation.centrifugation.forEach {
            centrifugation -> if (centrifugation != null) {
                val position = centrifugation.position
                centrifugationRepository.setResultForCentrifugation(centrifugation.id!!, resultStrings[position])
            }
        }
    }

    @Transactional
    fun getLatestSimulationResult(): ApiResult<Any> {
        try {
            val objectMapper = jacksonObjectMapper()
            objectMapper.registerModule(JavaTimeModule())

            val simulation = simulationRepository.findLatest()

            val result = bundleResult(simulation, objectMapper)

            return Success(result)
        }
        catch (e: NoSimulationFoundException) {
            return Failure(ErrorCode.NO_SIMULATION_FOUND, "No simulation found in DB")
        }
        catch (e: Exception) {
            return Failure(ErrorCode.GENERAL_ERROR, "An unexpected error occurred (${e.message}!")
        }
    }

    private fun bundleResult(simulation: Simulation?, objectMapper: ObjectMapper): SimulationResultDTO {
        if (simulation == null) {
            throw NoSimulationFoundException("No simulation found in DB")
        } else {
            val batchCultivationResult = bundleBatchCultivationResults(simulation, objectMapper)
            val centrifugationResult = bundleCentrifugationResults(simulation, objectMapper)

            return orderResults(batchCultivationResult, centrifugationResult)
        }
    }

    private fun bundleBatchCultivationResults(simulation: Simulation, objectMapper: ObjectMapper): MutableMap<Int, OperationResultDTO> {
        val batchCultivations: MutableMap<Int, OperationResultDTO> = mutableMapOf()
        simulation.batchCultivation.forEach { batch -> run {
                val position = batch?.position!!
                batchCultivations[position] = objectMapper.readValue<OperationResultDTO>(batch.result!!)
            }
        }
        return batchCultivations
    }

    private fun bundleCentrifugationResults(simulation: Simulation, objectMapper: ObjectMapper): MutableMap<Int, OperationResultDTO> {
        val centrifugations: MutableMap<Int, OperationResultDTO> = mutableMapOf()
        simulation.centrifugation.forEach { centrifugation -> run {
                val position = centrifugation?.position!!
                centrifugations[position] = objectMapper.readValue<OperationResultDTO>(centrifugation.result!!)
            }
        }
        return centrifugations
    }

    private fun orderResults(batchCultivations: MutableMap<Int, OperationResultDTO>, centrifugations: MutableMap<Int, OperationResultDTO>): SimulationResultDTO {
        val output: MutableList<OperationResultDTO> = mutableListOf()
        val order: MutableList<String> = mutableListOf()

        batchCultivations.forEach { batch -> run {
                output.add(batch.key, batch.value)
                order.add(batch.key, "batch-cultivation")
            }
        }

        centrifugations.forEach { centrifugation -> run {
                output.add(centrifugation.key, centrifugation.value)
                order.add(centrifugation.key, "centrifugation")
            }
        }

        return SimulationResultDTO(order, output)
    }

    @Transactional
    fun getKnownSettingsFromDb(): ApiResult<Any> {
        try {
            val microorganisms = microorganismRepository.findAllNames()

            val reactors = reactorRepository.findAllTypes()

            val impellers = impellerRepository.findAllTypes()

            val centrifuges = centrifugeRepository.findAllNames()

            val constants = KnownConstantsDTO(microorganisms, reactors, impellers, centrifuges)

            when {
                constants.microorganisms.isEmpty() -> throw NoConstantsFoundException("No microorganisms found in DB")
                constants.impellers.isEmpty() -> throw NoConstantsFoundException("No impellers found in DB")
                constants.reactors.isEmpty() -> throw NoConstantsFoundException("No reactors found in DB")
                constants.centrifuges.isEmpty() -> throw NoConstantsFoundException("No centrifuges found in DB")
                else -> return Success(constants)
            }
        }
        catch (e: NoConstantsFoundException) {
            return Failure(ErrorCode.NO_CONSTANTS_FOUND, "${e.message}")
        }
        catch (e: Exception) {
            return Failure(ErrorCode.GENERAL_ERROR, "An unexpected error occurred (${e.message}!")

        }
    }
}

sealed class ApiResult<out T> {
    internal data class Failure(val errorCode: ErrorCode, val errorMessage: String) : ApiResult<Nothing>()
    internal data class Success<T>(val value: T) : ApiResult<T>()
}

internal data class Operation(val type: String, val id: Long)

internal class EmptyStringException(msg: String): Exception(msg)

internal class NoSimulationFoundException(msg: String): Exception(msg)

internal class NoConstantsFoundException(msg: String): Exception(msg)

enum class ErrorCode {
    GENERAL_ERROR,
    EMPTY_STRING_INPUT,
    OPERATION_NOT_FOUND,
    NO_SIMULATION_FOUND,
    NO_CONSTANTS_FOUND,
}
