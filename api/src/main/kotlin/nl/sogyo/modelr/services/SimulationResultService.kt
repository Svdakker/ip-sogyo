package nl.sogyo.modelr.services

import nl.sogyo.modelr.ISimulation
import nl.sogyo.modelr.SimulationRepository
import nl.sogyo.modelr.models.OperationResultDTO
import org.springframework.stereotype.Service

@Service
class SimulationResultService(var simulationResultRepository: SimulationRepository) {

    fun addSimulationResult(simulation: ISimulation): OperationResultDTO {
        val result = simulation.runSimulation()

        return OperationResultDTO(id = 1L, duration = result.duration, model = result.model,
            costEstimation = result.costEstimation, powerConsumption = result.powerConsumption)
    }
}