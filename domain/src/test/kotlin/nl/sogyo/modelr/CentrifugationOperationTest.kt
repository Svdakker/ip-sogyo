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
        val expected = listOf(listOf(1.2879999999999999E-5, 1585591.8), listOf(1.932E-5, 1057061.2), listOf(2.5759999999999997E-5, 792795.9), listOf(3.22E-5, 634236.72), listOf(3.8639999999999996E-5, 528530.6), listOf(4.5079999999999995E-5, 453026.23), listOf(5.1519999999999995E-5,396397.95 ))
        val result = operation.modelOperation()

        assertEquals(expected, result)
    }

    @Test
    fun testEfficiencyOfSeparationCanBeCalculated() {
        val input = CentrifugationInput()
        val operation = CentrifugationOperation(input)

        val result = operation.calculateEfficiencyOfSeparation(input.centrifugationSettings.liquidFlowRate)

        assertEquals(634236.72, result)
    }

    @Test
    fun testDurationOfCentrifugationCanBeCalculated() {
        val input = CentrifugationInput()
        val operation = CentrifugationOperation(input)

        val result = operation.calculateDuration()

        assertEquals(0.45, result)
    }

    @Test
    fun testPowerConsumptionOfCentrifugationCanBeCalculated() {
        val input = CentrifugationInput()
        val operation = CentrifugationOperation(input)

        val result = operation.calculateEnergyConsumption()

        assertEquals(2.25, result.operations)
    }

    @Test
    fun testCostsOfCentrifugationOperationCanBeCalculated() {
        val input = CentrifugationInput()
        val operation = CentrifugationOperation(input)

        val result = operation.calculateCosts()

        assertEquals(0.34, result.energy)
    }
}