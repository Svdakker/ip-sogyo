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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.io.File
import java.time.LocalDate
import kotlin.math.exp

@RealDatabaseTest
@AutoConfigureMockMvc
class SimulationResultAcceptanceTest {

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
    fun `scenario retrieve simulation result is successful`() {
        //Setup
        val expected = File("src/test/resources/result.json").readText()
        val payload = File("src/test/resources/payload.json").readText()
        impellerRepository.save(Impeller("rushton turbine", 0.97, 0.72, 5.2))
        microorganismRepository.save(Microorganism(LocalDate.now(), "saccharomyces cerevisiae", 0.24,0.4,0.00703))
        reactorRepository.save(Reactor(LocalDate.now(), "example", 70.0,52.5, 9.29,3.10))
        costFactorRepository.save(CostFactor(LocalDate.now(), 0.15))
        centrifugeRepository.save(Centrifuge(LocalDate.now(), "example", 2.2E-2, 1.2E-3, 50, 45.0, 5.0))

        mockMvc.perform(post("/modelr/api/run-simulation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))

        //Act
        val result = mockMvc.perform(
            get("/modelr/api/simulation-result")
        )
            .andExpect(status().isOk)
            .andReturn()

        //Assert
        assertEquals(expected, result.response.contentAsString)
    }

    @Test
    fun `scenario retrieve simulation returns bad gateway when no simulations in db`() {
        //Setup
        val expectedStatus = 502
        val expectedResponse = "{\"errorCode\":\"NO_SIMULATION_FOUND\",\"errorMessage\":\"No simulation found in DB\"}"
        impellerRepository.save(Impeller("rushton turbine", 0.97, 0.72, 5.2))
        microorganismRepository.save(Microorganism(LocalDate.now(), "saccharomyces cerevisiae", 0.24,0.4,0.00703))
        reactorRepository.save(Reactor(LocalDate.now(), "example", 70.0,52.5, 9.29,3.10))
        costFactorRepository.save(CostFactor(LocalDate.now(), 0.15))
        centrifugeRepository.save(Centrifuge(LocalDate.now(), "example", 2.2E-2, 1.2E-3, 50, 45.0, 5.0))

        //Act
        val result = mockMvc.perform(
            get("/modelr/api/simulation-result")
        )
            .andExpect(status().isBadGateway)
            .andReturn()

        //Assert
        assertEquals(expectedStatus, result.response.status)
        assertEquals(expectedResponse, result.response.contentAsString)
    }

    @Test
    fun `scenario retrieve simulation result is successful for three subsequent batch cultivations`() {
        //Setup
        val expected = File("src/test/resources/resultThreeBatch.json").readText()
        val payload = File("src/test/resources/payloadThreeBatch.json").readText()
        impellerRepository.save(Impeller("rushton turbine", 0.97, 0.72, 5.2))
        microorganismRepository.save(Microorganism(LocalDate.now(), "saccharomyces cerevisiae", 0.24,0.4,0.00703))
        reactorRepository.save(Reactor(LocalDate.now(), "example", 70.0,52.5, 9.29,3.10))
        costFactorRepository.save(CostFactor(LocalDate.now(), 0.15))
        centrifugeRepository.save(Centrifuge(LocalDate.now(), "example", 2.2E-2, 1.2E-3, 50, 45.0, 5.0))

        mockMvc.perform(post("/modelr/api/run-simulation")
            .contentType(MediaType.APPLICATION_JSON)
            .content(payload))

        //Act
        val result = mockMvc.perform(
            get("/modelr/api/simulation-result")
        )
            .andExpect(status().isOk)
            .andReturn()

        //Assert
        assertEquals(expected, result.response.contentAsString)
    }

    @Test
    fun `scenario retrieve simulation result is successful for subsequent batch and centrifugation operations`() {
        //Setup
        val expected = File("src/test/resources/resultBatchCent.json").readText()
        val payload = File("src/test/resources/payloadBatchCent.json").readText()
        impellerRepository.save(Impeller("rushton turbine", 0.97, 0.72, 5.2))
        microorganismRepository.save(Microorganism(LocalDate.now(), "saccharomyces cerevisiae", 0.24,0.4,0.00703))
        reactorRepository.save(Reactor(LocalDate.now(), "example", 70.0,52.5, 9.29,3.10))
        costFactorRepository.save(CostFactor(LocalDate.now(), 0.15))
        centrifugeRepository.save(Centrifuge(LocalDate.now(), "example", 2.2E-2, 1.2E-3, 50, 45.0, 5.0))

        mockMvc.perform(post("/modelr/api/run-simulation")
            .contentType(MediaType.APPLICATION_JSON)
            .content(payload))

        //Act
        val result = mockMvc.perform(
            get("/modelr/api/simulation-result")
        )
            .andExpect(status().isOk)
            .andReturn()

        //Assert
        assertEquals(expected, result.response.contentAsString)
    }
}