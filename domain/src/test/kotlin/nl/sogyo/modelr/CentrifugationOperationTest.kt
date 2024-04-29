package nl.sogyo.modelr

import nl.sogyo.modelr.data.centrifugationRequest.CentrifugationInput
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class CentrifugationOperationTest {

    @Test
    fun testFiltrationOperationCanBeCreatedWithInput() {
        val input = CentrifugationInput()

        val result = CentrifugationOperation(input)

        assertNotNull(result)
    }

    @Test
    fun testEfficiencyOfSeparationCanBeCalculated() {
        val input = CentrifugationInput()
        val operation = CentrifugationOperation(input)

        val result = operation.calculateEfficiencyOfSeparation()

        assertEquals(2.08, result)
        assertTrue(result <= 1.0)
    }
}