package nl.sogyo.modelr.integration

import nl.sogyo.modelr.CentrifugeRepository
import nl.sogyo.modelr.RealDatabaseTest
import nl.sogyo.modelr.entities.Centrifuge
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.time.LocalDate

@RealDatabaseTest
class CentrifugeRepositoryIntegrationTest {

    @Autowired
    lateinit var centrifugeRepository: CentrifugeRepository

    @Test
    fun `Test findCentrifugeByName returns matching centrifuge`() {
        val centrifuge = Centrifuge(LocalDate.now(), "example", 2.2E-2, 1.2E-3, 50, 45.0, 5.0)
        centrifugeRepository.save(centrifuge)

        val result = centrifugeRepository.findCentrifugeByName("example")

        assertEquals(result?.id, 1L)
        assertEquals(result?.outerRadius, centrifuge.outerRadius)
        assertEquals(result?.innerRadius, centrifuge.innerRadius)
        assertEquals(result?.numberOfDisks, centrifuge.numberOfDisks)
        assertEquals(result?.diskAngle, centrifuge.diskAngle)
    }

    @Test
    fun `Test findAllNames returns all names of centrifuges in repository`() {
        val centrifuge = Centrifuge(LocalDate.now(), "example", 2.2E-2, 1.2E-3, 50, 45.0, 5.0)
        val centrifuge2 = Centrifuge(LocalDate.now(), "example2", 2.2E-2, 1.2E-3, 50, 45.0, 5.0)
        centrifugeRepository.save(centrifuge)
        centrifugeRepository.save(centrifuge2)

        val result = centrifugeRepository.findAllNames()

        assertEquals("example", result[0])
        assertEquals("example2", result[1])
    }
}