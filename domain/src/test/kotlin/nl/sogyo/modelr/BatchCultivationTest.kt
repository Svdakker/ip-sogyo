package nl.sogyo.modelr

import nl.sogyo.modelr.data.DataPoint
import nl.sogyo.modelr.data.CultivationInput
import nl.sogyo.modelr.data.OperationOutput
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class BatchCultivationTest {

    @Test
    fun testCalculateDuration() {
        val input = CultivationInput(1.0,20.00, 0.12, 0.27, 0.00703, 0.4)
        val operation = BatchCultivation(input)

        val result = operation.calculateDuration()

        assertEquals(15.57, result)
    }

    @Test
    fun testCalculateCellDensity() {
        val input = CultivationInput(1.0,20.00, 0.12, 0.27, 0.00703, 0.4)
        val operation = BatchCultivation(input)
        val timePoint = 10.00 //h

        val result = operation.calculateCellDensity(timePoint)

        assertEquals(1.79, result)
    }

    @Test
    fun testCalculateCellDensityAtTime0() {
        val input = CultivationInput(1.0,20.00, 0.12, 0.27, 0.00703, 0.4)
        val operation = BatchCultivation(input)
        val timePoint = 0.0 //h

        val result = operation.calculateCellDensity(timePoint)

        assertEquals(0.12, result)
    }

    @Test
    fun testCalculateSpecificSugarUptakeRate() {
        val input = CultivationInput(1.0,20.00, 0.12, 0.27, 0.00703, 0.4)
        val operation = BatchCultivation(input)

        val result = operation.calculateSugarUptakeRate()

        assertEquals(0.68, result)
    }

    @Test
    fun testCalculateSugarConcentration() {
        val input = CultivationInput(1.0,20.00, 0.12, 0.27, 0.00703, 0.4)
        val operation = BatchCultivation(input)
        val timePoint = 10.0

        val result = operation.calculateSugarConcentration(timePoint)

        assertEquals(7.83, result)
    }

    @Test
    fun testCalculateSugarConcentrationAtTime0() {
        val input = CultivationInput(1.0,20.0, 0.12, 0.27, 0.00703, 0.4)
        val operation = BatchCultivation(input)
        val timePoint = 0.0

        val result = operation.calculateSugarConcentration(timePoint)

        assertEquals(20.0, result)
    }

    @Test
    fun testCalculateModelDataPoint() {
        val input = CultivationInput(1.0, 20.00, 0.12, 0.27, 0.00703, 0.4)
        val operation = BatchCultivation(input)
        val timePoint = 10.0

        val expected = DataPoint(10.0,1.79, 7.83)
        val result = operation.calculateDataPoint(timePoint)

        assertEquals(expected, result)
    }

    @Test
    fun testProcessCanBeModeledOverTime() {
        val input = CultivationInput(1.0, 20.00, 0.12, 0.27, 0.00703, 0.4)
        val operation = BatchCultivation(input)

        val expected = listOf(DataPoint(time=0.0, cellDensity=0.12, sugarConcentration=20.0), DataPoint(time=1.0, cellDensity=0.16,
            sugarConcentration=19.89), DataPoint(time=2.0, cellDensity=0.21, sugarConcentration=19.71),
            DataPoint(time=3.0, cellDensity=0.27, sugarConcentration=19.45), DataPoint(time=4.0, cellDensity=0.35, sugarConcentration=19.05),
            DataPoint(time=5.0, cellDensity=0.46, sugarConcentration=18.44), DataPoint(time=6.0, cellDensity=0.61, sugarConcentration=17.51),
            DataPoint(time=7.0, cellDensity=0.79, sugarConcentration=16.24), DataPoint(time=8.0, cellDensity=1.04, sugarConcentration=14.34),
            DataPoint(time=9.0, cellDensity=1.36, sugarConcentration=11.68), DataPoint(time=10.0, cellDensity=1.79, sugarConcentration=7.83),
            DataPoint(time=11.0, cellDensity=2.34, sugarConcentration=2.5), DataPoint(time=12.0, cellDensity=3.06, sugarConcentration=-4.97))

        val result = operation.modelOperation()

        assertEquals(expected, result)
    }

    @Test
    fun testProcessOutputCanBeGenerated() {
        val input = CultivationInput(1.0, 20.00, 0.12, 0.27, 0.00703, 0.4)
        val operation = BatchCultivation(input)

        val expected = OperationOutput(duration = 15.57, model = listOf(DataPoint(time=0.0, cellDensity=0.12, sugarConcentration=20.0),
            DataPoint(time=1.0, cellDensity=0.16, sugarConcentration=19.89), DataPoint(time=2.0, cellDensity=0.21, sugarConcentration=19.71),
            DataPoint(time=3.0, cellDensity=0.27, sugarConcentration=19.45), DataPoint(time=4.0, cellDensity=0.35, sugarConcentration=19.05),
            DataPoint(time=5.0, cellDensity=0.46, sugarConcentration=18.44), DataPoint(time=6.0, cellDensity=0.61, sugarConcentration=17.51),
            DataPoint(time=7.0, cellDensity=0.79, sugarConcentration=16.24), DataPoint(time=8.0, cellDensity=1.04, sugarConcentration=14.34),
            DataPoint(time=9.0, cellDensity=1.36, sugarConcentration=11.68), DataPoint(time=10.0, cellDensity=1.79, sugarConcentration=7.83),
            DataPoint(time=11.0, cellDensity=2.34, sugarConcentration=2.5), DataPoint(time=12.0, cellDensity=3.06, sugarConcentration=-4.97)))

        val result = operation.generateOutput()

        assertEquals(expected, result)
    }
}