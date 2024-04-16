package nl.sogyo.modelr

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class BatchFermentationTest {

    @Test
    fun testCalculateDuration() {
        val input = FermentationInput(20.00, 0.12, 0.27, 0.00703, 0.4)
        val operation = BatchFermentation(input)

        val result = operation.calculateDuration()

        assertEquals(15.57, result)
    }
}