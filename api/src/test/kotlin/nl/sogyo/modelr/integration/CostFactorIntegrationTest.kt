package nl.sogyo.modelr.integration

import nl.sogyo.modelr.CostFactorRepository
import nl.sogyo.modelr.RealDatabaseTest
import nl.sogyo.modelr.entities.CostFactor
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.time.LocalDate

@RealDatabaseTest
class CostFactorIntegrationTest {

    @Autowired
    lateinit var costFactorRepository: CostFactorRepository

    @Test
    fun testFindCostFactorById() {
        val creation = LocalDate.now()
        val costFactor = CostFactor(creation, 0.15, null)
        costFactorRepository.save(costFactor)

        val result = costFactorRepository.findCostFactorById(1L)

        assertEquals(costFactor.date, result.date)
        assertEquals(costFactor.energy, result.energy)
    }
}