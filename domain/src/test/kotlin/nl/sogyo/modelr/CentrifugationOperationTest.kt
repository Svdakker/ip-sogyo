package nl.sogyo.modelr

import nl.sogyo.modelr.data.centrifugationRequest.CentrifugationInput
import nl.sogyo.modelr.data.centrifugationRequest.CentrifugationSettings
import nl.sogyo.modelr.data.centrifugationRequest.CentrifugeProperties
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class CentrifugationOperationTest {

    @Test
    fun testCentrifugationOperationCanBeCreatedWithInput() {
        val input = CentrifugationInput(CentrifugeProperties(8.0E-2, 2.2E-3, 50, 45.0,  5.0),         CentrifugationSettings(9200.0, 3.22E-5, 52.5E-3))

        CentrifugationSettings(9200.0, 3.22E-5, 52.5E-3)
        val result = CentrifugationOperation(input)

        assertNotNull(result)
    }

    @Test
    fun testCentrifugationOperationCanBeModeledForVaryingFlowRate() {
        val input = CentrifugationInput(CentrifugeProperties(8.0E-2, 2.2E-3, 50, 45.0,  5.0),         CentrifugationSettings(9200.0, 3.22E-5, 52.5E-3))
        val operation = CentrifugationOperation(input)
        val expected = listOf(listOf(1.2879999999999999E-5, 1585591.8, 1.13, 5.65, 0.85), listOf(1.932E-5, 1057061.2, 0.75, 3.75, 0.56), listOf(2.5759999999999997E-5, 792795.9, 0.57, 2.85, 0.43), listOf(3.22E-5, 634236.72, 0.45, 2.25, 0.34), listOf(3.8639999999999996E-5, 528530.6, 0.38, 1.9, 0.29), listOf(4.5079999999999995E-5, 453026.23, 0.32, 1.6, 0.24), listOf(5.1519999999999995E-5, 396397.95, 0.28, 1.4, 0.21))
        val result = operation.modelOperation()

        assertEquals(expected, result)
    }

    @Test
    fun testEfficiencyOfSeparationCanBeCalculated() {
        val input = CentrifugationInput(CentrifugeProperties(8.0E-2, 2.2E-3, 50, 45.0,  5.0),         CentrifugationSettings(9200.0, 3.22E-5, 52.5E-3))
        val operation = CentrifugationOperation(input)

        val result = operation.calculateEfficiencyOfSeparation(input.centrifugationSettings.liquidFlowRate)

        assertEquals(634236.72, result)
    }

    @Test
    fun testDurationOfCentrifugationCanBeCalculated() {
        val input = CentrifugationInput(CentrifugeProperties(8.0E-2, 2.2E-3, 50, 45.0,  5.0), CentrifugationSettings(9200.0, 3.22E-5, 52.5E-3))
        val operation = CentrifugationOperation(input)

        val result = operation.calculateDuration()

        assertEquals(0.45, result)
    }

    @Test
    fun testPowerConsumptionOfCentrifugationCanBeCalculated() {
        val input = CentrifugationInput(CentrifugeProperties(8.0E-2, 2.2E-3, 50, 45.0,  5.0),         CentrifugationSettings(9200.0, 3.22E-5, 52.5E-3))
        val operation = CentrifugationOperation(input)

        val result = operation.calculateEnergyConsumption()

        assertEquals(2.25, result.operations)
    }

    @Test
    fun testCostsOfCentrifugationOperationCanBeCalculated() {
        val input = CentrifugationInput(CentrifugeProperties(8.0E-2, 2.2E-3, 50, 45.0,  5.0),         CentrifugationSettings(9200.0, 3.22E-5, 52.5E-3))
        val operation = CentrifugationOperation(input)

        val result = operation.calculateCosts()

        assertEquals(0.34, result.energy)
    }
}