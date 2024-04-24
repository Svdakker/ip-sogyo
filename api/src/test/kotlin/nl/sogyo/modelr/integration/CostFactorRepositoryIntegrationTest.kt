package nl.sogyo.modelr.integration

import nl.sogyo.modelr.CostFactorRepository
import nl.sogyo.modelr.RealDatabaseTest
import nl.sogyo.modelr.entities.CostFactor
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.time.LocalDate
import java.time.Month

@RealDatabaseTest
class CostFactorRepositoryIntegrationTest {

    @Autowired
    lateinit var costFactorRepository: CostFactorRepository

    @Test
    fun `test findAllByDate returns all costFactors at a given date`() {
        val creation = LocalDate.now()
        val costFactor = CostFactor(creation, 0.15, null)
        val costFactor2 = CostFactor(creation, 0.25, null)
        costFactorRepository.save(costFactor)
        costFactorRepository.save(costFactor2)

        val result = costFactorRepository.findAllByDate(creation)

        assertEquals(2, result.size)
        assertEquals(costFactor.id, result[0].id)
        assertEquals(costFactor2.id, result[1].id)
        assertTrue(result[1].id != result[0].id)
    }

    @Test
    fun testFindLatestByDate() {
        val creation = LocalDate.of(2019, Month.APRIL, 2)
        val costFactor = CostFactor(creation, 0.15, null)
        val creation2 = LocalDate.of(2022, Month.JUNE, 5)
        val costFactor2 = CostFactor(creation2, 0.25, null)
        costFactorRepository.save(costFactor)
        costFactorRepository.save(costFactor2)

        val result = costFactorRepository.findFirstByOrderByDateDesc()

        assertEquals(creation2, result?.date)
    }

    @Test
    fun testFindCostFactorById() {
        val creation = LocalDate.of(2019, Month.APRIL, 2)

        val result = costFactorRepository.findCostFactorById(1L)

        assertEquals(creation, result.date)
        assertEquals(0.15, result.energy)
    }
}