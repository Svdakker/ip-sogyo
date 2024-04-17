package nl.sogyo.modelr

import nl.sogyo.modelr.data.DataPoint
import nl.sogyo.modelr.models.SimulationRequestDTO
import nl.sogyo.modelr.models.SimulationResultDTO
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate

@SpringBootTest(classes = [ModelrApplication::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SimulationResultDTOrepositoryIntegrationTest(@Autowired var restTemplate: TestRestTemplate) {

    var simulationResultId: Long = 0

    @Test
    fun addNewSimulationResult() {
        val input = SimulationRequestDTO("batch", 1.0,20.00, 0.12,
            0.27, 0.00703, 0.4)
        val result = this.restTemplate.postForEntity("/modelr/api/run", input, SimulationResultDTO::class.java)
        val expectedModel = listOf(
            DataPoint(time=0.0, cellDensity=0.12, sugarConcentration=20.0), DataPoint(time=1.0, cellDensity=0.16,
            sugarConcentration=19.89), DataPoint(time=2.0, cellDensity=0.21, sugarConcentration=19.71),
            DataPoint(time=3.0, cellDensity=0.27, sugarConcentration=19.45), DataPoint(time=4.0, cellDensity=0.35, sugarConcentration=19.05),
            DataPoint(time=5.0, cellDensity=0.46, sugarConcentration=18.44), DataPoint(time=6.0, cellDensity=0.61, sugarConcentration=17.51),
            DataPoint(time=7.0, cellDensity=0.79, sugarConcentration=16.24), DataPoint(time=8.0, cellDensity=1.04, sugarConcentration=14.34),
            DataPoint(time=9.0, cellDensity=1.36, sugarConcentration=11.68), DataPoint(time=10.0, cellDensity=1.79, sugarConcentration=7.83),
            DataPoint(time=11.0, cellDensity=2.34, sugarConcentration=2.5), DataPoint(time=12.0, cellDensity=3.06, sugarConcentration=-4.97)
        )
        simulationResultId = result.body?.id!!
        assertTrue { result.body?.duration == 15.57 }
        assertTrue { result.body?.model == expectedModel }
    }
}