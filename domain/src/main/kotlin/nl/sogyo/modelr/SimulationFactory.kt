package nl.sogyo.modelr

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

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

        val config = objectMapper.readValue<FermentationInput>(settings)

        return Simulation(BatchFermentation(config))
    }
}