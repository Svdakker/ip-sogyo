package nl.sogyo.modelr

import nl.sogyo.modelr.data.SelectedOperations
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class SimulationFactoryTest {

    @Test
    fun testCreateNewSimulation() {
        val factory = SimulationFactory()
        val operations = """{"operationType":"batch"}"""
        val settings = """{"operationType":"batch","initialSugarConcentration":20.0,"initialCellDensity":0.12,"maxGrowthRate":0.27,"maintenance":0.00703,"yield":0.4}"""

        val result = factory.createNewSimulation(operations, settings)

        assertNotNull(result)
    }

    @Test
    fun testSimulationInputAnalysis() {
        val factory = SimulationFactory()
        val operations = """{"operationType":"batch"}"""

        val result: SelectedOperations = factory.analyzeInput(operations)

        assertEquals(result.operationType, "batch")
    }
}