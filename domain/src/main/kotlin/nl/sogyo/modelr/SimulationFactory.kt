package nl.sogyo.modelr

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import nl.sogyo.modelr.data.CultivationInput
import nl.sogyo.modelr.data.SelectedOperations

class SimulationFactory : ISimulationFactory {

    override fun createNewSimulation(operations: String, settings: String): Simulation {

        return createFirstUnitOperation(settings)
    }

    fun analyzeInput(operations: String): SelectedOperations {
        val objectMapper = jacksonObjectMapper()

        return objectMapper.readValue<SelectedOperations>(operations)
    }

    private fun createFirstUnitOperation(settings: String): Simulation {
        val objectMapper = jacksonObjectMapper()

        val config = objectMapper.readValue<CultivationInput>(settings)

        return Simulation(BatchCultivation(config))
    }
}