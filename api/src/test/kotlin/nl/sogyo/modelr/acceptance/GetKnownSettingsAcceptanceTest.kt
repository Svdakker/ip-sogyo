package nl.sogyo.modelr.acceptance

import nl.sogyo.modelr.ImpellerRepository
import nl.sogyo.modelr.MicroorganismRepository
import nl.sogyo.modelr.ReactorRepository
import nl.sogyo.modelr.RealDatabaseTest
import nl.sogyo.modelr.entities.Impeller
import nl.sogyo.modelr.entities.Microorganism
import nl.sogyo.modelr.entities.Reactor
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDate

@RealDatabaseTest
@AutoConfigureMockMvc
class GetKnownSettingsAcceptanceTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var impellerRepository: ImpellerRepository

    @Autowired
    private lateinit var microorganismRepository: MicroorganismRepository

    @Autowired
    private lateinit var reactorRepository: ReactorRepository

    @Test
    fun `scenario retrieve simulation result is successful`() {
        //Setup
        val expected = """{"value":{"microorganisms":["saccharomyces cerevisiae"],"reactors":["example"],"impellers":["rushton turbine"]}}"""
        impellerRepository.save(Impeller("rushton turbine", 0.97, 0.72, 5.2))
        microorganismRepository.save(Microorganism(LocalDate.now(), "saccharomyces cerevisiae", 0.24,0.4,0.00703))
        reactorRepository.save(Reactor(LocalDate.now(), "example", 70.0,52.5, 9.29,3.10))

        //Act
        val result = mockMvc.perform(
            get("/modelr/api/constants")
        )
            .andExpect(status().isOk)
            .andReturn()

        //Assert
        assertEquals(expected, result.response.contentAsString)
    }
}