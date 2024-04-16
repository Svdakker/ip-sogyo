package nl.sogyo.modelr

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class SimulationTest {

    @Test
    fun testSimulationCanBeCreated() {
        val simulation = Simulation(BatchFermentation(FermentationInput(20.00, 0.12, 0.27, 0.00703, 0.4)))

        assertNotNull(simulation)
    }

    @Test
    fun testSimulationStartsWithUnitOperation() {
        val simulation = Simulation(BatchFermentation(FermentationInput(20.00, 0.12, 0.27, 0.00703, 0.4)))

        assertNotNull(simulation.getFirstUnitOperation())
    }

    @Test
    fun testRunSimulationReturnsDuration() {
        val simulation = Simulation(BatchFermentation(FermentationInput(20.00, 0.12, 0.27, 0.00703, 0.4)))

        val result = simulation.runSimulation().duration

        assertEquals(15.57, result)
    }
}