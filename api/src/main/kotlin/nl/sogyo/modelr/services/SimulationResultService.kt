package nl.sogyo.modelr.services

import nl.sogyo.modelr.ISimulation
import nl.sogyo.modelr.SimulationRepository
import nl.sogyo.modelr.entities.Reactor
import nl.sogyo.modelr.models.SimulationResultDTO
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class SimulationResultService(var simulationResultRepository: SimulationRepository) {

    fun addSimulationResult(simulation: ISimulation): SimulationResultDTO {
        val result = simulation.runSimulation()

        return SimulationResultDTO(id = 1L, duration = result.duration, model = result.model,
            costEstimation = result.costEstimation, powerConsumption = result.powerConsumption)
    }
}