package nl.sogyo.modelr

import org.junit.jupiter.api.Assertions.*
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

    @Test
    fun testIllegalOperationThrowsException() {
        val factory = SimulationFactory()
        val operations = listOf("random-operation")
        val settings = File("src/test/resources/simulationSettings.json").readText()

        assertThrows(IllegalArgumentException::class.java) {
            factory.createNewSimulation(operations, settings)
        }
    }
}