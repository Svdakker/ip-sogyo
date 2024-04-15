package nl.sogyo.modelr.controllers

import nl.sogyo.modelr.ISimulation
import nl.sogyo.modelr.ISimulationFactory
import nl.sogyo.modelr.models.SimulationResultDTO
import nl.sogyo.modelr.services.SimulationResultService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api")
class ModelrController(var simulationFactory: ISimulationFactory, var simulationResultService: SimulationResultService) {

    @GetMapping("/run")
    fun runSimulation(): SimulationResultDTO {

        val simulation: ISimulation = simulationFactory.createNewSimulation()

        return simulationResultService.addSimulationResult(simulation)
    }
}
