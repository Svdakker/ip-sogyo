package nl.sogyo.modelr.services

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import nl.sogyo.modelr.*
import nl.sogyo.modelr.models.BatchCultivationRequestDTO
import nl.sogyo.modelr.entities.BatchCultivation
import nl.sogyo.modelr.services.ApiResult.Success
import nl.sogyo.modelr.services.ApiResult.Failure
import nl.sogyo.modelr.entities.Simulation
import nl.sogyo.modelr.models.OperationsDTO
import nl.sogyo.modelr.models.SimulationRequestDTO
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SimulationRequestService(
    private val simulationRepository: SimulationRepository,
    private val batchCultivationRepository: BatchCultivationRepository,
    private val microorganismRepository: MicroorganismRepository,
    private val reactorRepository: ReactorRepository,
    private val impellerRepository: ImpellerRepository,
    private val costFactorRepository: CostFactorRepository
) {

    @Transactional
    fun saveNewSimulationRequest(request: SimulationRequestDTO): ApiResult<Any> {
        try {
            val objectMapper = jacksonObjectMapper()

            val operations = OperationsDTO()

            if(request.batchCultivation != null) {
                val batchRequest = objectMapper.writeValueAsString(request.batchCultivation)
                val batchCultivation = mapRequestToBatchCultivation(request.batchCultivation!!, batchRequest)
                batchCultivationRepository.save(batchCultivation)
                operations.batchCultivation = batchRequest
            }

            val batch = batchCultivationRepository.findByRequest(operations.batchCultivation)

            val requestedSimulation  = Simulation(batch, null)

            return Success(simulationRepository.save(requestedSimulation).id!!)

        } catch (e: Exception) {
            return Failure(ErrorCode.GENERAL_ERROR, "An unexpected error occurred (${e.message}!")
        }
    }

    private fun mapRequestToBatchCultivation(request: BatchCultivationRequestDTO, batchRequest: String): BatchCultivation {
        val microorganism = microorganismRepository.findMicroorganismsByName(request.cultivationSettings.microorganism)

        val reactor = reactorRepository.findReactorByName(request.reactorSettings.reactorType)

        val impeller = impellerRepository.findImpellerByType(request.reactorSettings.impellerType)

        val costFactor = costFactorRepository.findFirstByOrderByDateDesc()

        return BatchCultivation(1, batchRequest, null, costFactor!!, microorganism!!, reactor!!, impeller!!, null)
    }
}

sealed class ApiResult<out T> {
    internal data class Failure(val errorCode: ErrorCode, val errorMessage: String) : ApiResult<Nothing>()
    internal data class Success<T>(val value: T) : ApiResult<T>()
}


enum class ErrorCode {
    GENERAL_ERROR,
}
