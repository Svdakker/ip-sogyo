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
        val request = """{"operationType":"batch-cultivation","cultivationSettings":{"microorganism":"saccharomyces cerevisiae","accuracy":0.01,"initialSugarConcentration":20.0,"initialCellDensity":0.12,"maxGrowthRate":0.0,"maintenance":0.0,"yield":0.0},"reactorSettings":{"reactorType":"example","nominalVolume":0.0,"workingVolume":0.0,"height":0.0,"width":0.0,"impellerType":"rushton turbine","numberOfImpellers":4.0,"agitatorSpeed":2.5}}"""
        val date = LocalDate.now()
        val microorganism = Microorganism(date, "saccharomyces cerevisiae", 0.24, 0.4, 0.00703, null)
        val reactor = Reactor(date, "example", 70.0,52.5,9.29,3.10, null)
        val impeller = Impeller("rushton turbine", 0.97, 0.72, 5.2, null)
        val costFactor = CostFactor(date, 0.15, null)
        val batchCultivation = BatchCultivation(1, request, null, costFactor, microorganism, reactor, impeller, null)
        batchCultivationRepository.save(batchCultivation)

        val result = batchCultivationRepository.findByRequest(request)

        assertEquals(batchCultivation.id, result?.id)
    }
}