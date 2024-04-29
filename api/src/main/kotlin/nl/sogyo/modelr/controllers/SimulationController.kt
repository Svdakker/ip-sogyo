package nl.sogyo.modelr.controllers

import nl.sogyo.modelr.ISimulationFactory
import nl.sogyo.modelr.models.SimulationRequestDTO
import nl.sogyo.modelr.services.SimulationService
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import nl.sogyo.modelr.services.ApiResult.Success
import nl.sogyo.modelr.services.ApiResult.Failure
import nl.sogyo.modelr.services.ErrorCode
import org.springframework.http.HttpStatus.OK
import org.springframework.http.HttpStatus.BAD_GATEWAY
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.web.bind.annotation.GetMapping

@RestController
@RequestMapping("modelr/api")
class SimulationController(
    private val simulationFactory: ISimulationFactory,
    private val simulationService: SimulationService,
    ) {

    @PostMapping("/run-simulation",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE])
    fun runSimulationRequest(@RequestBody request: SimulationRequestDTO): ResponseEntity<out Any> {
        return simulationService.runNewSimulation(request, simulationFactory).let { result ->
            when (result) {
                is Success -> handleSuccess(result)
                is Failure -> handleFailure(result)
            }
        }
    }

    @GetMapping("/simulation-result", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getSimulationResult(): ResponseEntity<out Any> {
        return simulationService.getLatestSimulationResult().let { result ->
            when (result) {
                is Success -> handleSuccess(result)
                is Failure -> handleFailure(result)
            }
        }
    }

    @GetMapping("/constants", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getKnownSettings(): ResponseEntity<out Any> {
        return simulationService.getKnownSettingsFromDb().let { result ->
            when (result) {
                is Success -> handleSuccess(result)
                is Failure -> handleFailure(result)
            }
        }
    }

    private fun handleSuccess(result: Success<Any>): ResponseEntity<SuccessDto> {
        return ResponseEntity.status(OK).body(SuccessDto(result.value))
    }

    private fun handleFailure(
        result: Failure
    ): ResponseEntity<ErrorDto> {

        val code = result.errorCode
        val status = when (code) {
            ErrorCode.GENERAL_ERROR -> INTERNAL_SERVER_ERROR
            ErrorCode.OPERATION_NOT_FOUND -> BAD_REQUEST
            ErrorCode.EMPTY_STRING_INPUT -> BAD_REQUEST
            ErrorCode.NO_SIMULATION_FOUND -> BAD_GATEWAY
            ErrorCode.NO_CONSTANTS_FOUND -> BAD_GATEWAY
        }
        val errorDto = ErrorDto(code.name, result.errorMessage)
        return ResponseEntity.status(status).body(errorDto)
    }
}

data class ErrorDto(val errorCode: String, val errorMessage: String)

data class SuccessDto(val value: Any)