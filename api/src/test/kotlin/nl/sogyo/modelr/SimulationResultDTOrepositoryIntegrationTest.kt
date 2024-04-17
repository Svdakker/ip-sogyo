package nl.sogyo.modelr

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
        val expectedModel = mapOf(0.0 to listOf(0.12, 20.0),1.0 to listOf(0.16, 19.89), 2.0 to listOf(0.21, 19.71), 3.0 to listOf(0.27, 19.45),
            4.0 to listOf(0.35, 19.05), 5.0 to listOf(0.46, 18.44), 6.0 to listOf(0.61, 17.51), 7.0 to listOf(0.79, 16.24), 8.0 to listOf(1.04, 14.34),
            9.0 to listOf(1.36, 11.68), 10.0 to listOf(1.79, 7.83), 11.0 to listOf(2.34, 2.5), 12.0 to listOf(3.06, -4.97))
        simulationResultId = result.body?.id!!
        assertTrue { result.body?.duration == 15.57 }
        assertTrue { result.body?.model == expectedModel }
    }
}
