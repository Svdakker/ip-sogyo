package nl.sogyo.modelr.controllers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import nl.sogyo.modelr.ISimulation
import nl.sogyo.modelr.ISimulationFactory
import nl.sogyo.modelr.models.BatchCultivationRequestDTO
import nl.sogyo.modelr.models.SimulationRequestDTO
import nl.sogyo.modelr.models.SimulationResultDTO
import nl.sogyo.modelr.services.SimulationRequestService
import nl.sogyo.modelr.services.SimulationResultService
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import nl.sogyo.modelr.services.ApiResult.Success
import nl.sogyo.modelr.services.ApiResult.Failure
import nl.sogyo.modelr.services.ErrorCode
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR

@RestController
@RequestMapping("modelr/api")
class SimulationController(
    private val simulationFactory: ISimulationFactory,
    private val simulationResultService: SimulationResultService,
    private val simulationRequestService: SimulationRequestService,
    ) {

    @PostMapping("/run")
    fun runSimulation(@RequestBody input: BatchCultivationRequestDTO): SimulationResultDTO {

        val objectMapper = jacksonObjectMapper()

        val operations = objectMapper.writeValueAsString(input.operationType)

        val settings = objectMapper.writeValueAsString(input)

        val simulation: ISimulation = simulationFactory.createNewSimulation(operations, settings)

        return simulationResultService.addSimulationResult(simulation)
    }

    @PostMapping("/save-request",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE])
    fun saveSimulationRequest(@RequestBody request: SimulationRequestDTO): ResponseEntity<out Any> {
        return simulationRequestService.saveNewSimulation(request).let { result ->
            when (result) {
                is Success -> handleSuccess(result)
                is Failure -> handleFailure(result)
            }
        }
    }

    private fun handleSuccess(result: Success<Any>): ResponseEntity<SuccessDto> {
        return ResponseEntity.status(CREATED).body(SuccessDto(result.value))
    }

    private fun handleFailure(
        result: Failure
    ): ResponseEntity<ErrorDto> {

        val code = result.errorCode
        val status = when (code) {
            ErrorCode.GENERAL_ERROR -> INTERNAL_SERVER_ERROR
        }
        val errorDto = ErrorDto(code.name, result.errorMessage)
        return ResponseEntity.status(status).body(errorDto)
    }
}

data class ErrorDto(val errorCode: String, val errorMessage: String)

data class SuccessDto(val simulationId: Any)