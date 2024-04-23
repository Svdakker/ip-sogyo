package nl.sogyo.modelr.integration

import nl.sogyo.modelr.ModelrApplication
import nl.sogyo.modelr.data.CostEstimation
import nl.sogyo.modelr.data.DataPoint
import nl.sogyo.modelr.data.PowerConsumption
import nl.sogyo.modelr.models.CultivationSettingsDTO
import nl.sogyo.modelr.models.ReactorSettingsDTO
import nl.sogyo.modelr.models.SimulationRequestDTO
import nl.sogyo.modelr.models.SimulationResultDTO
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate

@SpringBootTest(classes = [ModelrApplication::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReactorDTOrepositoryIntegrationTest(@Autowired var restTemplate: TestRestTemplate) {

    var simulationResultId: Long = 0

    @Test
    fun addNewSimulationResult() {
        val input = SimulationRequestDTO("batch-cultivation", CultivationSettingsDTO("",1.0,20.00, 0.12, 0.27, 0.00703, 0.4), reactorSettings = ReactorSettingsDTO("",70.0,52.5,9.29,3.10,"rushton turbine",4.0,2.5))
        val result = this.restTemplate.postForEntity("/modelr/api/run", input, SimulationResultDTO::class.java)
        val expectedModel = listOf(DataPoint(time=0.0, cellDensity=0.12, sugarConcentration=20.0), DataPoint(time=1.0, cellDensity=0.16,
            sugarConcentration=19.89), DataPoint(time=2.0, cellDensity=0.21, sugarConcentration=19.71),
            DataPoint(time=3.0, cellDensity=0.27, sugarConcentration=19.45), DataPoint(time=4.0, cellDensity=0.35, sugarConcentration=19.05),
            DataPoint(time=5.0, cellDensity=0.46, sugarConcentration=18.43), DataPoint(time=6.0, cellDensity=0.61, sugarConcentration=17.5),
            DataPoint(time=7.0, cellDensity=0.79, sugarConcentration=16.23), DataPoint(time=8.0, cellDensity=1.04, sugarConcentration=14.33),
            DataPoint(time=9.0, cellDensity=1.36, sugarConcentration=11.65), DataPoint(time=10.0, cellDensity=1.79, sugarConcentration=7.79),
            DataPoint(time=11.0, cellDensity=2.34, sugarConcentration=2.44), DataPoint(time=12.0, cellDensity=3.06, sugarConcentration=-5.04)
        )
        val expectedCostEstimation = CostEstimation(651.81)
        val expectedPowerConsumption = PowerConsumption(4345.41)

        simulationResultId = result.body?.id!!
        assertTrue { result.body?.duration == 15.57 }
        assertTrue { result.body?.model == expectedModel }
        assertTrue { result.body?.costEstimation == expectedCostEstimation }
        assertTrue { result.body?.powerConsumption == expectedPowerConsumption }
    }
}
