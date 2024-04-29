package nl.sogyo.modelr.integration

import nl.sogyo.modelr.MicroorganismRepository
import nl.sogyo.modelr.RealDatabaseTest
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
    fun testFindByOrganismNameIsReturningMatchingOrganism() {
        val creation = LocalDate.now()
        val microorganism = Microorganism(creation, "Saccharomyces cerevisiae", 0.24, 0.4, 0.00703, null)
        microorganismRepository.save(microorganism)

        val result = microorganismRepository.findMicroorganismsByName("Saccharomyces cerevisiae")

        assertEquals(microorganism.id, result?.id)
        assertEquals(microorganism.date, result?.date)
        assertEquals(microorganism.name, result?.name)
        assertEquals(microorganism.maxGrowthRate, result?.maxGrowthRate)
        assertEquals(microorganism.yield, result?.yield)
        assertEquals(microorganism.maintenance, result?.maintenance)
    }
}