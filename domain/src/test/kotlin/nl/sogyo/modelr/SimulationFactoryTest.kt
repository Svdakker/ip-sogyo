package nl.sogyo.modelr

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class SimulationFactoryTest {

    @Test
    fun testCreateNewSimulation() {
        val factory = SimulationFactory()

        val result = factory.createNewSimulation()

        assertNotNull(result)
    }
}