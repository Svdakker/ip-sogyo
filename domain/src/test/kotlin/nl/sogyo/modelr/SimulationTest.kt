package nl.sogyo.modelr

import nl.sogyo.modelr.data.BatchCultivationInput
import nl.sogyo.modelr.data.CultivationSettings
import nl.sogyo.modelr.data.ReactorSettings
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class SimulationTest {

    @Test
    fun testSimulationCanBeCreated() {
        val simulation = Simulation(BatchCultivation(BatchCultivationInput(cultivationSettings = CultivationSettings(1.0,20.00, 0.12, 0.27, 0.00703, 0.4), reactorSettings = ReactorSettings())))

        assertNotNull(simulation)
    }

    @Test
    fun testSimulationStartsWithUnitOperation() {
        val simulation = Simulation(BatchCultivation(BatchCultivationInput(cultivationSettings = CultivationSettings(1.0,20.00, 0.12, 0.27, 0.00703, 0.4), reactorSettings = ReactorSettings())))

        assertNotNull(simulation.getFirstUnitOperation())
    }

    @Test
    fun testRunSimulationReturnsDuration() {
        val simulation = Simulation(BatchCultivation(BatchCultivationInput(cultivationSettings = CultivationSettings(1.0,20.00, 0.12, 0.27, 0.00703, 0.4), reactorSettings = ReactorSettings())))

        val result = simulation.runSimulation().duration

        assertEquals(15.57, result)
    }
}