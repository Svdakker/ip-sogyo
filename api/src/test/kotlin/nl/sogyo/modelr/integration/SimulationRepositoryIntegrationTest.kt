package nl.sogyo.modelr.integration

import nl.sogyo.modelr.RealDatabaseTest
import nl.sogyo.modelr.SimulationRepository
import nl.sogyo.modelr.entities.Simulation
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@RealDatabaseTest
class SimulationRepositoryIntegrationTest {

    @Autowired
    private lateinit var simulationRepository: SimulationRepository

    @Test
    fun `Test findLatest returns the most recent simulation`() {
        val simulation1 = Simulation()
        val simulation2 = Simulation()
        simulationRepository.save(simulation1)
        simulationRepository.save(simulation2)

        val result = simulationRepository.findLatest()

        assertEquals(simulation2.id, result?.id)
    }
}