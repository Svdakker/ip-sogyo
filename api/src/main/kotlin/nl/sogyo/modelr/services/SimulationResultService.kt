package nl.sogyo.modelr.services

import nl.sogyo.modelr.ISimulation
import nl.sogyo.modelr.ISimulationResultRepository
import nl.sogyo.modelr.entities.SimulationResult
import nl.sogyo.modelr.models.SimulationResultDTO
import org.springframework.stereotype.Service

@Service
class SimulationResultService(var simulationResultRepository: ISimulationResultRepository) {

    fun addSimulationResult(simulation: ISimulation): SimulationResultDTO {
        val result = simulation.runSimulation()

        val save = simulationResultRepository.save(SimulationResult(id = null, duration = result.duration))

        return SimulationResultDTO(id = save.id!!, duration = save.duration, model = result.model,
            costEstimation = result.costEstimation, powerConsumption = result.powerConsumption)
    }
}