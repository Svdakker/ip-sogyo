package nl.sogyo.modelr

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import nl.sogyo.modelr.data.*

class SimulationFactory : ISimulationFactory {

    override fun createNewSimulation(operations: List<String>, settings: String): Simulation {
        val objectMapper = jacksonObjectMapper()

        val simulationInput = objectMapper.readValue<SimulationInput>(settings)

        return when (operations[0]) {
            "batch-cultivation" -> createBatchCultivation(simulationInput.batchCultivationInput!!)
            else -> throw IllegalArgumentException("Unsupported operation")
        }
    }

    fun analyzeInput(operations: String): SelectedOperations {
        val objectMapper = jacksonObjectMapper()

        return objectMapper.readValue<SelectedOperations>(operations)
    }

    private fun createBatchCultivation(config: BatchCultivationInput): Simulation {
        val validInputCheck = isValidInput(config)

        if (validInputCheck != "valid") {
            throw IllegalArgumentException("Input $validInputCheck invalid: negative values")
        }

        return Simulation(BatchCultivation(config))
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
                || cultivationSettings.maxGrowthRate < 0.0 || cultivationSettings.maintenance < 0.0 || cultivationSettings.yield < 0.0)
    }

    private fun isValidReactorSettings(reactorSettings: ReactorSettings): Boolean {
        return !(reactorSettings.nominalVolume < 0.0 || reactorSettings.workingVolume < 0.0 || reactorSettings.height < 0.0
                || reactorSettings.width < 0.0 || reactorSettings.numberOfImpellers < 0.0 || reactorSettings.agitatorSpeed < 0.0)
    }
}