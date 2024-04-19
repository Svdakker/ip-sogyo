package nl.sogyo.modelr

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import nl.sogyo.modelr.data.BatchCultivationInput
import nl.sogyo.modelr.data.CultivationSettings
import nl.sogyo.modelr.data.ReactorSettings
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DataHandlingTest {

    @Test
    fun testInitializationOfBatchCultivationInputDataClass() {
        val settings = "{}"
        val expected = BatchCultivationInput(CultivationSettings(1.0,20.00, 0.12, 0.27, 0.00703, 0.4), ReactorSettings())

        val objectMapper = jacksonObjectMapper()

        val result = objectMapper.readValue<BatchCultivationInput>(settings)

        assertEquals(expected, result)
    }
}