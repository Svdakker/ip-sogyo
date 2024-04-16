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
        val input = SimulationRequestDTO("batch", 20.00, 0.12,
            0.27, 0.00703, 0.4)
        val result = this.restTemplate.postForEntity("/modelr/api/run", input, SimulationResultDTO::class.java)
        simulationResultId = result.body?.id!!
        assertTrue { result.body?.duration == 15.57 }
    }
}
