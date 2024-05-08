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
}