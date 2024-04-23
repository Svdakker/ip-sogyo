package nl.sogyo.modelr.acceptance

import nl.sogyo.modelr.RealDatabaseTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.io.File

@RealDatabaseTest
@AutoConfigureMockMvc
class SimulationRequestAcceptanceTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `scenario save simulation request is successful`() {
        val payload = File("src/test/resources/payload.json").readText()

        val result = mockMvc.perform(
            post("api/save-request")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload)
        )
            .andExpect(status().isCreated)
            .andReturn()

        assertEquals("{\"id\": \"11111\"", result.response.contentAsString)
    }
}