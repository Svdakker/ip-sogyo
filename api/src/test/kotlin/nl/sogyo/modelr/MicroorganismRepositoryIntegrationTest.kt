package nl.sogyo.modelr

import nl.sogyo.modelr.entities.Microorganism
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.time.LocalDate

@RealDatabaseTest
class MicroorganismRepositoryIntegrationTest {

    @Autowired
    lateinit var microorganismRepository: MicroorganismRepository

    @Test
    fun testGetByOrganismNameIsReturningMatchingOrganism() {
        val creation = LocalDate.now()
        val microorganism = Microorganism(creation, "Saccharomyces cerevisiae", 0.24, 0.4, 0.00703, null)
        microorganismRepository.save(microorganism)

        val result = microorganismRepository.findMicroorganismsByName("Saccharomyces cerevisiae")

        assertEquals(microorganism.name, result?.name)
        assertEquals(microorganism.maxGrowthRate, result?.maxGrowthRate)
        assertEquals(microorganism.yield, result?.yield)
        assertEquals(microorganism.maintenance, result?.maintenance)
    }
}