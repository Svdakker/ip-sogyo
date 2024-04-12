package nl.sogyo.modelr

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class SimulationTest {

    @Test
    fun testSimulationCanBeCreated() {
        val simulation = Simulation()

        assertNotNull(simulation)
    }

    @Test
    fun testSimulationStartsWithUnitOperation() {
        val simulation = Simulation()

        assertNotNull(simulation.getFirstUnitOperation())
    }

    @Test
    fun testRunSimulationReturnsDuration() {
        val simulation = Simulation()

        val result = simulation.runSimulation()

        assertEquals(result, 100)
    }
}