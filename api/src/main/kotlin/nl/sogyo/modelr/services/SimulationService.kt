package nl.sogyo.modelr.services

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import nl.sogyo.modelr.*
import nl.sogyo.modelr.models.BatchCultivationRequestDTO
import nl.sogyo.modelr.entities.BatchCultivation
import nl.sogyo.modelr.services.ApiResult.Success
import nl.sogyo.modelr.services.ApiResult.Failure
import nl.sogyo.modelr.entities.Simulation
import nl.sogyo.modelr.models.SimulationRequestDTO
import nl.sogyo.modelr.models.OperationResultDTO
import nl.sogyo.modelr.models.SimulationResultDTO
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SimulationService(
    private val simulationRepository: SimulationRepository,
    private val batchCultivationRepository: BatchCultivationRepository,
    private val microorganismRepository: MicroorganismRepository,
    private val reactorRepository: ReactorRepository,
    private val impellerRepository: ImpellerRepository,
    private val costFactorRepository: CostFactorRepository
) {

    @Transactional
    fun runNewSimulation(request: SimulationRequestDTO, factory: ISimulationFactory): ApiResult<Any> {
        try {
            val objectMapper = jacksonObjectMapper()

            val simulationId = saveNewSimulation(request)

            val operations = request.order

            val settings = objectMapper.writeValueAsString(simulationRepository.findById(simulationId).get())

            val simulation: ISimulation = factory.createNewSimulation(operations, settings)

            val result = simulation.runSimulation()

            val resultDTO = bundleResult(result)

            return Success(resultDTO)
        }
        catch (e: IllegalArgumentException){
            return Failure(ErrorCode.OPERATION_NOT_FOUND, "${e.message}")
        }
        catch (e: Exception) {
            return Failure(ErrorCode.GENERAL_ERROR, "An unexpected error occurred (${e.message}!")
        }
    }

    fun saveNewSimulation(request: SimulationRequestDTO): Long {
        val objectMapper = jacksonObjectMapper()

        val operations = request.order.map { operationType ->
            saveUnitOperation(operationType, request, request.order.indexOf(operationType), objectMapper)
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

    private fun saveUnitOperation(operation: String, request: SimulationRequestDTO, position: Int, objectMapper: ObjectMapper): UnitOperation {
        return when (operation) {
            "batch-cultivation" -> saveBatchCultivation(request.batchCultivation!!, position, objectMapper)
            else -> throw IllegalArgumentException("Unit operation not found ($operation)")
        }
    }

    private fun saveBatchCultivation(request: BatchCultivationRequestDTO, position: Int, objectMapper: ObjectMapper): UnitOperation {
        val batchRequest = objectMapper.writeValueAsString(request)

        val microorganism = microorganismRepository.findMicroorganismsByName(request.cultivationSettings.microorganism)

        val reactor = reactorRepository.findReactorByName(request.reactorSettings.reactorType)

        val impeller = impellerRepository.findImpellerByType(request.reactorSettings.impellerType)

        val costFactor = costFactorRepository.findFirstByOrderByDateDesc()

        val batchCultivation = BatchCultivation(position, batchRequest, null, costFactor!!, microorganism!!, reactor!!, impeller!!)

        return UnitOperation("batch-cultivation", batchCultivationRepository.save(batchCultivation).id!!)
    }

}

sealed class ApiResult<out T> {
    internal data class Failure(val errorCode: ErrorCode, val errorMessage: String) : ApiResult<Nothing>()
    internal data class Success<T>(val value: T) : ApiResult<T>()
}

internal data class UnitOperation(val type: String, val id: Long)

enum class ErrorCode {
    GENERAL_ERROR,
    OPERATION_NOT_FOUND
}
