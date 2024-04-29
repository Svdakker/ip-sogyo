package nl.sogyo.modelr.services

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import nl.sogyo.modelr.*
import nl.sogyo.modelr.data.OperationOutput
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
    private val requestRepository: RequestRepository,
    private val cultivationSettingsRepository: CultivationSettingsRepository,
    private val reactorSettingsRepository: ReactorSettingsRepository,
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

            val result = simulation.runSimulation()

            saveResult(result, objectMapper, simulationId)

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
        val operations = request.order.map { operationType ->
            saveUnitOperation(operationType, request, request.order.indexOf(operationType))
        }

        val simulation = createSimulation(operations)

        return simulationRepository.save(simulation).id!!
    }

    private fun createSimulation(operations: List<UnitOperation>): Simulation {
        var batch: BatchCultivation? = null
        operations.map {operation ->
            when (operation.type) {
                "batch-cultivation" -> { batch = batchCultivationRepository.findById(operation.id).get() }
                else -> throw IllegalArgumentException("Unexpected operation type $operation")
            }
        }
        return Simulation(batchCultivation = batch)
    }

    private fun saveUnitOperation(operation: String, request: SimulationRequestDTO, position: Int): UnitOperation {
        return when (operation) {
            "batch-cultivation" -> saveBatchCultivation(request.batchCultivation!!, position)
            else -> throw IllegalArgumentException("Unit operation not found ($operation)")
        }
    }

    private fun saveBatchCultivation(request: BatchCultivationRequestDTO, position: Int): UnitOperation {
        val requestId = saveRequest(request)

        val savedRequest = requestRepository.findById(requestId)

        val microorganism = microorganismRepository.findMicroorganismsByName(request.cultivationSettings.microorganism)

        val reactor = reactorRepository.findReactorByName(request.reactorSettings.reactorType)

        val impeller = impellerRepository.findImpellerByType(request.reactorSettings.impellerType)

        val costFactor = costFactorRepository.findFirstByOrderByDateDesc()

        val batchCultivation = BatchCultivation(position, savedRequest.get(), null, costFactor!!, microorganism!!, reactor!!, impeller!!)

        return UnitOperation("batch-cultivation", batchCultivationRepository.save(batchCultivation).id!!)
    }

    private fun saveRequest(request: BatchCultivationRequestDTO): Long {
        val operationType = request.operationType

        val cultivationSettingsId = saveCultivationSettings(request.cultivationSettings)

        val reactorSettingsId = saveReactorSettings(request.reactorSettings)

        val cultivationSettings = cultivationSettingsRepository.findById(cultivationSettingsId)

        val reactorSettings = reactorSettingsRepository.findById(reactorSettingsId)

        return requestRepository.save(Request(operationType, reactorSettings.get(), cultivationSettings.get())).id!!
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

    private fun saveResult(result: OperationOutput, objectMapper: ObjectMapper, simulationId: Long) {
        val simulation = simulationRepository.findById(simulationId).get()

        if (simulation.batchCultivation != null) {
            val resultString = objectMapper.writeValueAsString(result)
            batchCultivationRepository.setResultForBatchCultivation(simulation.batchCultivation!!.id!!, resultString)
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
            val batchCultivationResult = checkForBatchCultivationResult(simulation, objectMapper)

            return SimulationResultDTO(batchCultivationResult)
        }
    }

    private fun checkForBatchCultivationResult(simulation: Simulation, objectMapper: ObjectMapper): OperationResultDTO? {
        if (simulation.batchCultivation == null) {
            return null
        } else {
            val batchCultivation = simulation.batchCultivation!!
            val batchResult = batchCultivation.result
            return objectMapper.readValue<OperationResultDTO>(batchResult!!)
        }
    }

    @Transactional
    fun getKnownSettingsFromDb(): ApiResult<Any> {
        try {
            val microorganisms = microorganismRepository.findAllNames()

            val reactors = reactorRepository.findAllTypes()

            val impellers = impellerRepository.findAllTypes()

            val constants = KnownConstantsDTO(microorganisms, reactors, impellers)

            when {
                constants.microorganisms.isEmpty() -> throw NoConstantsFoundException("No microorganisms found in DB")
                constants.impellers.isEmpty() -> throw NoConstantsFoundException("No impellers found in DB")
                constants.reactors.isEmpty() -> throw NoConstantsFoundException("No reactors found in DB")
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

internal data class UnitOperation(val type: String, val id: Long)

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
