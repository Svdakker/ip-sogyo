package nl.sogyo.modelr

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import nl.sogyo.modelr.data.*

class SimulationFactory : ISimulationFactory {

    override fun createNewSimulation(operations: List<String>, settings: String): Simulation {
        val objectMapper = jacksonObjectMapper()

        val simulationInput = objectMapper.readValue<SimulationInput>(settings)

        return when (operations[0]) {
            "batch-cultivation" -> createBatchCultivation(simulationInput.batchCultivation!!)
            else -> throw IllegalArgumentException("Unsupported operation")
        }
    }

    private fun createBatchCultivation(request: BatchCultivation): Simulation {
        val batchCultivationInput = selectInput(request)

        val validInput = isValidInput(batchCultivationInput)

        if (validInput != "valid") {
            throw IllegalArgumentException("Invalid input, $validInput contains negative numbers")
        }

        val batchCultivation = BatchCultivationCalc(batchCultivationInput)

        return Simulation(batchCultivation)
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


    private fun isValidInput(config: BatchCultivationInput): String {
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