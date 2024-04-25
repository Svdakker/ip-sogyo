package nl.sogyo.modelr

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import java.io.File

class SimulationFactoryTest {

    @Test
    fun testCreateNewSimulation() {
        val factory = SimulationFactory()
        val operations = listOf("batch-cultivation")
        val settings = File("src/test/resources/simulationSettings.json").readText()

        val result = factory.createNewSimulation(operations, settings)

        assertNotNull(result)
    }
}