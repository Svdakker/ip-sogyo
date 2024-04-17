package nl.sogyo.modelr.controllers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import nl.sogyo.modelr.ISimulation
import nl.sogyo.modelr.ISimulationFactory
import nl.sogyo.modelr.models.SimulationRequestDTO
import nl.sogyo.modelr.models.SimulationResultDTO
import nl.sogyo.modelr.services.SimulationResultService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("modelr/api")
class ModelrController(var simulationFactory: ISimulationFactory, var simulationResultService: SimulationResultService) {

    @PostMapping("/run")
    fun runSimulation(@RequestBody input: SimulationRequestDTO): SimulationResultDTO {

        val objectMapper = jacksonObjectMapper()

        val operations = objectMapper.writeValueAsString(input.operationType)

        val settings = objectMapper.writeValueAsString(input)

        val simulation: ISimulation = simulationFactory.createNewSimulation(operations, settings)

        return simulationResultService.addSimulationResult(simulation)
    }
}
