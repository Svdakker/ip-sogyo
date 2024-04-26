package nl.sogyo.modelr.acceptance

import nl.sogyo.modelr.*
import nl.sogyo.modelr.entities.CostFactor
import nl.sogyo.modelr.entities.Impeller
import nl.sogyo.modelr.entities.Microorganism
import nl.sogyo.modelr.entities.Reactor
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.io.File
import java.time.LocalDate

@RealDatabaseTest
@AutoConfigureMockMvc
class SimulationRequestAcceptanceTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var impellerRepository: ImpellerRepository

    @Autowired
    private lateinit var microorganismRepository: MicroorganismRepository

    @Autowired
    private lateinit var reactorRepository: ReactorRepository

    @Autowired
    private lateinit var costFactorRepository: CostFactorRepository

    @Test
    fun `scenario save simulation request is successful`() {
        //Setup
        val payload = File("src/test/resources/payload.json").readText()
        impellerRepository.save(Impeller("rushton turbine", 0.97, 0.72, 5.2))
        microorganismRepository.save(Microorganism(LocalDate.now(), "saccharomyces cerevisiae", 0.24,0.4,0.00703))
        reactorRepository.save(Reactor(LocalDate.now(), "example", 70.0,52.5, 9.29,3.10))
        costFactorRepository.save(CostFactor(LocalDate.now(), 0.15))

        //Act
        val result = mockMvc.perform(
            post("/modelr/api/run-simulation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload)
        )
            .andExpect(status().isCreated)
            .andReturn()

        //Assert
        assertEquals("{\"value\":1}", result.response.contentAsString)
    }
}