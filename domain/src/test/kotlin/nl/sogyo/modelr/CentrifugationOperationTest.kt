package nl.sogyo.modelr

import nl.sogyo.modelr.data.centrifugationRequest.CentrifugationInput
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class CentrifugationOperationTest {

    @Test
    fun testCentrifugationOperationCanBeCreatedWithInput() {
        val input = CentrifugationInput()

        val result = CentrifugationOperation(input)

        assertNotNull(result)
    }

    @Test
    fun testCentrifugationOperationCanBeModeledForVaryingFlowRate() {
        val input = CentrifugationInput()
        val operation = CentrifugationOperation(input)
        val expected = listOf(listOf(3.22E-5, 634236.72), listOf(3.8639999999999996E-5, 528530.6), listOf(4.6367999999999994E-5, 440442.17), listOf(5.564159999999999E-5, 367035.14), listOf(6.22E-5, 328334.77))

        val result = operation.modelOperation()

        assertEquals(expected, result)
    }

    @Test
    fun testEfficiencyOfSeparationCanBeCalculated() {
        val input = CentrifugationInput()
        val operation = CentrifugationOperation(input)

        val result = operation.calculateEfficiencyOfSeparation(input.centrifugationSettings.minLiquidFlowRate)

        assertEquals(634236.72, result)
    }

    @Test
    fun testDurationOfCentrifugationCanBeCalculated() {
        val input = CentrifugationInput()
        val operation = CentrifugationOperation(input)

        val result = operation.calculateDuration()

        assertEquals(100.0, result)
    }

    @Test
    fun testPowerConsumptionOfCentrifugationCanBeCalculated() {
        val input = CentrifugationInput()
        val operation = CentrifugationOperation(input)

        val result = operation.calculateEnergyConsumption()

        assertEquals(100.0, result.operations)
    }

    @Test
    fun testCostsOfCentrifugationOperationCanBeCalculated() {
        val input = CentrifugationInput()
        val operation = CentrifugationOperation(input)

        val result = operation.calculateCosts()

        assertEquals(100.0, result.energy)
    }
}