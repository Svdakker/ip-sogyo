package nl.sogyo.modelr

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import nl.sogyo.modelr.data.*
import nl.sogyo.modelr.data.batchCultivationRequest.BatchCultivation
import nl.sogyo.modelr.data.batchCultivationRequest.BatchCultivationInput
import nl.sogyo.modelr.data.batchCultivationRequest.CultivationSettings
import nl.sogyo.modelr.data.batchCultivationRequest.ReactorSettings

class SimulationFactory : ISimulationFactory {

    override fun createNewSimulation(operations: List<String>, settings: String): Simulation {
        val objectMapper = jacksonObjectMapper()
        objectMapper.registerModule(JavaTimeModule())

        val simulationInput = objectMapper.readValue<SimulationInput>(settings)

        return if (operations.isEmpty()) {
            throw IllegalArgumentException("No operations found")
        } else {
            Simulation(checkNextOperation(operations, simulationInput, listOf(0))!!)
        }
    }

    private fun checkNextOperation(operations: List<String>, input: SimulationInput, accumulator: List<Int>): UnitOperation? {
        return if (operations.size == accumulator.sum()) {
            null
        } else {
            createNextOperation(operations, accumulator, input)
        }
    }

    private fun createNextOperation(operations: List<String>, accumulator: List<Int>, input: SimulationInput) =
        when (operations[accumulator.sum()]) {
        "batch-cultivation" -> {
            BatchCultivationOperation(checkBatchCultivationInput(input.batchCultivation[accumulator[0]]!!),
                nextOperation = checkNextOperation(operations, input, listOf(accumulator[0] + 1)))
        }
        else -> throw IllegalArgumentException("Unsupported operation")
    }

    private fun checkBatchCultivationInput(request: BatchCultivation): BatchCultivationInput {
        val batchCultivationInput = selectInput(request)

        val validInput = isValidBatchCultivationInput(batchCultivationInput)

        if (validInput != "valid") {
            throw IllegalArgumentException("Invalid input, $validInput contains negative numbers")
        } else {
            return batchCultivationInput
        }
    }

    private fun selectInput(request: BatchCultivation): BatchCultivationInput {
        val cultivationSettings = selectCultivationSettings(request)
        val reactorSettings = selectReactorSettings(request)

        return BatchCultivationInput(cultivationSettings, reactorSettings)
    }

    private fun selectCultivationSettings(request: BatchCultivation): CultivationSettings {
        val microorganism = request.microorganism
        val requestedCultivation = request.request.cultivationSettings

        val maxGrowthRate = requestedCultivation.maxGrowthRate ?: microorganism.maxGrowthRate
        val maintenance = requestedCultivation.maintenance ?: microorganism.maintenance
        val yield = requestedCultivation.yield ?: microorganism.yield

        return CultivationSettings(
            accuracy = requestedCultivation.accuracy,
            initialSugarConcentration = requestedCultivation.initialSugarConcentration,
            initialCellDensity = requestedCultivation.initialCellDensity,
            maxGrowthRate = maxGrowthRate,
            maintenance= maintenance,
            yield = yield)
    }

    private fun selectReactorSettings(request: BatchCultivation): ReactorSettings {
        val reactor = request.reactor
        val impeller = request.impeller
        val requestedReactor = request.request.reactorSettings

        val nominalVolume = requestedReactor.nominalVolume ?: reactor.nominalVolume
        val workingVolume = requestedReactor.workingVolume ?: reactor.workingVolume
        val height = requestedReactor.height ?: reactor.height
        val width = requestedReactor.width ?: reactor.width

        return ReactorSettings(
            agitatorSpeed = requestedReactor.agitatorSpeed,
            numberOfImpellers = requestedReactor.numberOfImpellers,
            impellerDiameter = impeller.impellerDiameter,
            impellerType = impeller.type,
            impellerPowerNumber = impeller.impellerPowerNumber,
            impellerFlowNumber = impeller.impellerFlowNumber,
            nominalVolume = nominalVolume,
            workingVolume = workingVolume,
            height = height,
            width = width)
    }


    private fun isValidBatchCultivationInput(config: BatchCultivationInput): String {
        val cultivationSettings = config.cultivationSettings
        val reactorSettings = config.reactorSettings
        return when {
            !isValidCultivationSettings(cultivationSettings) -> "cultivation settings"
            !isValidReactorSettings(reactorSettings) -> "reactor settings"
            else -> "valid"
        }
    }

    private fun isValidCultivationSettings(cultivationSettings: CultivationSettings): Boolean {
        return !(cultivationSettings.accuracy < 0.001 || cultivationSettings.initialSugarConcentration < 0.0 || cultivationSettings.initialCellDensity < 0.0
                || cultivationSettings.maxGrowthRate!! < 0.0 || cultivationSettings.maintenance!! < 0.0 || cultivationSettings.yield!! < 0.0)
    }

    private fun isValidReactorSettings(reactorSettings: ReactorSettings): Boolean {
        return !(reactorSettings.numberOfImpellers < 0.0 || reactorSettings.agitatorSpeed < 0.0 ||
                reactorSettings.height!! < 0.0 || reactorSettings.width!! < 0.0 || reactorSettings.nominalVolume!! < 0.0
                || reactorSettings.workingVolume!! < 0)
    }
}