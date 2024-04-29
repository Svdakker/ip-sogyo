package nl.sogyo.modelr.integration

import nl.sogyo.modelr.BatchCultivationRepository
import nl.sogyo.modelr.RealDatabaseTest
import nl.sogyo.modelr.entities.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.time.LocalDate

@RealDatabaseTest
class BatchRepositoryIntegrationTest {

    @Autowired
    lateinit var batchCultivationRepository: BatchCultivationRepository

    @Test
    fun `test findByRequest returns matching batchCultivation`() {
        val request = Request("batch-cultivation", ReactorSettings("example", impellerType = "rushton turbine", numberOfImpellers = 4, agitatorSpeed = 2.5), CultivationSettings("saccharomyces cerevisiae", 0.01, 20.0,0.12))
        val date = LocalDate.now()
        val microorganism = Microorganism(date, "saccharomyces cerevisiae", 0.24, 0.4, 0.00703)
        val reactor = Reactor(date, "example", 70.0,52.5,9.29,3.10)
        val impeller = Impeller("rushton turbine", 0.97, 0.72, 5.2)
        val costFactor = CostFactor(date, 0.15)
        val batchCultivation = BatchCultivation(1, request, null, costFactor, microorganism, reactor, impeller)
        batchCultivationRepository.save(batchCultivation)

        val result = batchCultivationRepository.findByRequest(request)

        assertEquals(batchCultivation.id, result?.id)
    }
}