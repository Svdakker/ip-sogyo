package nl.sogyo.modelr

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class BatchFermentationTest {

    @Test
    fun testCalculateDuration() {
        val operation = BatchFermentation()

        val result = operation.calculateDuration()

        assertEquals(result, 100)
    }
}