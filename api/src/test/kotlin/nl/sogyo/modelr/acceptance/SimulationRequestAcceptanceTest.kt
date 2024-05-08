package nl.sogyo.modelr.acceptance

import nl.sogyo.modelr.*
import nl.sogyo.modelr.entities.*
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

    @Autowired
    private lateinit var centrifugeRepository: CentrifugeRepository

    @Test
    fun `scenario run simulation request is successful`() {
        //Setup
        val payload = File("src/test/resources/payload.json").readText()
        impellerRepository.save(Impeller("rushton turbine", 0.97, 0.72, 5.2))
        microorganismRepository.save(Microorganism(LocalDate.of(2024,4,25), "saccharomyces cerevisiae", 0.24,0.4,0.00703))
        reactorRepository.save(Reactor(LocalDate.of(2024,4,25), "example", 70.0,52.5, 9.29,3.10))
        costFactorRepository.save(CostFactor(LocalDate.of(2024,4,25), 0.15))

        //Act
        val result = mockMvc.perform(
            post("/modelr/api/run-simulation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload)
        )
            .andExpect(status().isOk)
            .andReturn()

        //Assert
        assertEquals("{\"value\":1}", result.response.contentAsString)

    }

    @Test
    fun `scenario run simulation request returns bad request when unknown operation is requested`() {
        //Setup
        val expectedStatus = 400
        val expectedResponse = "{\"errorCode\":\"OPERATION_NOT_FOUND\",\"errorMessage\":\"Unit operation not found (random-operation)\"}"
        val payload = File("src/test/resources/payload3.json").readText()
        impellerRepository.save(Impeller("rushton turbine", 0.97, 0.72, 5.2))
        microorganismRepository.save(Microorganism(LocalDate.of(2024,4,25), "saccharomyces cerevisiae", 0.24,0.4,0.00703))
        reactorRepository.save(Reactor(LocalDate.of(2024,4,25), "example", 70.0,52.5, 9.29,3.10))
        costFactorRepository.save(CostFactor(LocalDate.of(2024,4,25), 0.15))

        //Act
        val result = mockMvc.perform(
            post("/modelr/api/run-simulation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload)
        )
            .andExpect(status().isBadRequest)
            .andReturn()

        //Assert
        assertEquals(expectedStatus, result.response.status)
        assertEquals(expectedResponse, result.response.contentAsString)
    }

    @Test
    fun `scenario run simulation request is successful for two simulations with the same costFactor, microorganism, reactor and impeller`() {
        //Setup
        val payload = File("src/test/resources/payload.json").readText()
        val payload2 = File("src/test/resources/payload2.json").readText()
        impellerRepository.save(Impeller("rushton turbine", 0.97, 0.72, 5.2))
        microorganismRepository.save(Microorganism(LocalDate.of(2024,4,25), "saccharomyces cerevisiae", 0.24,0.4,0.00703))
        reactorRepository.save(Reactor(LocalDate.of(2024,4,25), "example", 70.0,52.5, 9.29,3.10))
        costFactorRepository.save(CostFactor(LocalDate.of(2024,4,25), 0.15))

        //Act
        val result = mockMvc.perform(
            post("/modelr/api/run-simulation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload)
        )
            .andExpect(status().isOk)
            .andReturn()

        val result2 =  mockMvc.perform(
            post("/modelr/api/run-simulation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload2)
        )
            .andExpect(status().isOk)
            .andReturn()

        //Assert
        assertEquals("{\"value\":1}", result.response.contentAsString)
        assertEquals("{\"value\":2}", result2.response.contentAsString)
    }

    @Test
    fun `Scenario run simulation request is successful for simulation consisting of cascade of three batch cultivations`() {
        //Setup
        val payload = File("src/test/resources/payloadThreeBatch.json").readText()
        impellerRepository.save(Impeller("rushton turbine", 0.97, 0.72, 5.2))
        microorganismRepository.save(Microorganism(LocalDate.of(2024,4,25), "saccharomyces cerevisiae", 0.24,0.4,0.00703))
        reactorRepository.save(Reactor(LocalDate.of(2024,4,25), "example", 70.0,52.5, 9.29,3.10))
        costFactorRepository.save(CostFactor(LocalDate.of(2024,4,25), 0.15))

        //Act
        val result = mockMvc.perform(
            post("/modelr/api/run-simulation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload)
        )
            .andExpect(status().isOk)
            .andReturn()

        //Assert
        assertEquals("{\"value\":1}", result.response.contentAsString)
    }

    @Test
    fun `Scenario run simulation request is successful for simulation with cascade of batch cultivation and centrifugation`() {
        //Setup
        val payload = File("src/test/resources/payloadBatchCent.json").readText()
        impellerRepository.save(Impeller("rushton turbine", 0.97, 0.72, 5.2))
        microorganismRepository.save(Microorganism(LocalDate.of(2024,4,25), "saccharomyces cerevisiae", 0.24,0.4,0.00703))
        reactorRepository.save(Reactor(LocalDate.of(2024,4,25), "example", 70.0,52.5, 9.29,3.10))
        centrifugeRepository.save(Centrifuge(LocalDate.now(), "example", 2.2E-2, 1.2E-3, 50, 45.0, 5.0))
        costFactorRepository.save(CostFactor(LocalDate.of(2024,4,25), 0.15))

        //Act
        val result = mockMvc.perform(
            post("/modelr/api/run-simulation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload)
        )
            .andExpect(status().isOk)
            .andReturn()

        //Assert
        assertEquals("{\"value\":1}", result.response.contentAsString)
    }
}