package nl.sogyo.modelr

import nl.sogyo.modelr.data.FermentationInput
import nl.sogyo.modelr.data.OperationOutput
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

    @Test
    fun testCalculateCellDensity() {
        val input = FermentationInput(20.00, 0.12, 0.27, 0.00703, 0.4)
        val operation = BatchFermentation(input)
        val timePoint = 10.00 //h

        val result = operation.calculateCellDensity(timePoint)

        assertEquals(1.79, result)
    }

    @Test
    fun testCalculateCellDensityAtTime0() {
        val input = FermentationInput(20.00, 0.12, 0.27, 0.00703, 0.4)
        val operation = BatchFermentation(input)
        val timePoint = 0.0 //h

        val result = operation.calculateCellDensity(timePoint)

        assertEquals(0.12, result)
    }

    @Test
    fun testCalculateSpecificSugarUptakeRate() {
        val input = FermentationInput(20.00, 0.12, 0.27, 0.00703, 0.4)
        val operation = BatchFermentation(input)

        val result = operation.calculateSugarUptakeRate()

        assertEquals(0.68, result)
    }

    @Test
    fun testCalculateSugarConcentration() {
        val input = FermentationInput(20.00, 0.12, 0.27, 0.00703, 0.4)
        val operation = BatchFermentation(input)
        val timePoint = 10.0

        val result = operation.calculateSugarConcentration(timePoint)

        assertEquals(7.83, result)
    }

    @Test
    fun testCalculateSugarConcentrationAtTime0() {
        val input = FermentationInput(20.0, 0.12, 0.27, 0.00703, 0.4)
        val operation = BatchFermentation(input)
        val timePoint = 0.0

        val result = operation.calculateSugarConcentration(timePoint)

        assertEquals(20.0, result)
    }

    @Test
    fun testCalculateModelDataPoint() {
        val input = FermentationInput(20.00, 0.12, 0.27, 0.00703, 0.4)
        val operation = BatchFermentation(input)
        val timePoint = 10.0

        val expected = listOf(1.79, 7.83)
        val result = operation.calculateDataPoint(timePoint)

        assertEquals(expected, result)
    }

    @Test
    fun testProcessCanBeModeledOverTime() {
        val input = FermentationInput(20.00, 0.12, 0.27, 0.00703, 0.4)
        val operation = BatchFermentation(input)
        val interval = 1.0 //h

        val expected = mapOf(0.0 to listOf(0.12, 20.0),1.0 to listOf(0.16, 19.89), 2.0 to listOf(0.21, 19.71), 3.0 to listOf(0.27, 19.45),
            4.0 to listOf(0.35, 19.05), 5.0 to listOf(0.46, 18.44), 6.0 to listOf(0.61, 17.51), 7.0 to listOf(0.79, 16.24), 8.0 to listOf(1.04, 14.34),
            9.0 to listOf(1.36, 11.68), 10.0 to listOf(1.79, 7.83), 11.0 to listOf(2.34, 2.5), 12.0 to listOf(3.06, -4.97))

        val result = operation.modelOperation(interval)

        assertEquals(expected, result)
    }

    @Test
    fun testProcessOutputCanBeGenerated() {
        val input = FermentationInput(20.00, 0.12, 0.27, 0.00703, 0.4)
        val operation = BatchFermentation(input)
        val interval = 1.0

        val expected = OperationOutput(duration = 15.57, model = mapOf(0.0 to listOf(0.12, 20.0),1.0 to listOf(0.16, 19.89), 2.0 to listOf(0.21, 19.71), 3.0 to listOf(0.27, 19.45),
            4.0 to listOf(0.35, 19.05), 5.0 to listOf(0.46, 18.44), 6.0 to listOf(0.61, 17.51), 7.0 to listOf(0.79, 16.24), 8.0 to listOf(1.04, 14.34),
            9.0 to listOf(1.36, 11.68), 10.0 to listOf(1.79, 7.83), 11.0 to listOf(2.34, 2.5), 12.0 to listOf(3.06, -4.97)))
        val result = operation.generateOutput(interval)

        assertEquals(expected, result)
    }
}