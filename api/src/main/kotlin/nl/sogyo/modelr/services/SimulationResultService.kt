package nl.sogyo.modelr.services

import nl.sogyo.modelr.ISimulation
import nl.sogyo.modelr.ISimulationRepository
import nl.sogyo.modelr.entities.SimulationResult
import nl.sogyo.modelr.models.SimulationResultDTO
import org.springframework.stereotype.Service

@Service
class SimulationResultService(var simulationRepository: ISimulationRepository) {

    fun addSimulationResult(simulation: ISimulation): SimulationResultDTO {
        val result = simulation.runSimulation()

        val save = simulationRepository.save(SimulationResult(id = null, duration = result))

        return SimulationResultDTO(id = save.id!!, duration = save.duration)
    }
}