package nl.sogyo.modelr

import nl.sogyo.modelr.data.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class BatchCultivationTest {

    @Test
    fun testCalculateDuration() {
        val input = BatchCultivationInput(cultivationSettings = CultivationSettings(1.0,20.00, 0.12, 0.27, 0.00703, 0.4), reactorSettings = ReactorSettings())
        val operation = BatchCultivation(input)

        val result = operation.calculateDuration()

        assertEquals(15.57, result)
    }

    @Test
    fun testCalculateCellDensity() {
        val input = BatchCultivationInput(cultivationSettings = CultivationSettings(1.0,20.00, 0.12, 0.27, 0.00703, 0.4), reactorSettings = ReactorSettings())
        val operation = BatchCultivation(input)
        val timePoint = 10.00 //h

        val result = operation.calculateCellDensity(timePoint)

        assertEquals(1.79, result)
    }

    @Test
    fun testCalculateCellDensityAtTime0() {
        val input = BatchCultivationInput(cultivationSettings = CultivationSettings(1.0,20.00, 0.12, 0.27, 0.00703, 0.4), reactorSettings = ReactorSettings())
        val operation = BatchCultivation(input)
        val timePoint = 0.0 //h

        val result = operation.calculateCellDensity(timePoint)

        assertEquals(0.12, result)
    }

    @Test
    fun testCalculateFinalCellDensity() {
        val input = BatchCultivationInput(cultivationSettings = CultivationSettings(1.0,20.00, 0.12, 0.27, 0.00703, 0.4), reactorSettings = ReactorSettings())
        val operation = BatchCultivation(input)

        val result = operation.calculateFinalCellDensity()

        assertEquals(8.037540284151723, result)
    }

    @Test
    fun testCalculateSpecificSugarUptakeRate() {
        val input = BatchCultivationInput(cultivationSettings = CultivationSettings(1.0,20.00, 0.12, 0.27, 0.00703, 0.4), reactorSettings = ReactorSettings())
        val operation = BatchCultivation(input)

        val result = operation.calculateSugarUptakeRate()

        assertEquals(0.68203, result)
    }

    @Test
    fun testCalculateSugarConcentration() {
        val input = BatchCultivationInput(cultivationSettings = CultivationSettings(1.0,20.00, 0.12, 0.27, 0.00703, 0.4), reactorSettings = ReactorSettings())
        val operation = BatchCultivation(input)
        val timePoint = 10.0

        val result = operation.calculateSugarConcentration(timePoint)

        assertEquals(7.79, result)
    }

    @Test
    fun testCalculateSugarConcentrationAtTime0() {
        val input = BatchCultivationInput(cultivationSettings = CultivationSettings(1.0,20.00, 0.12, 0.27, 0.00703, 0.4), reactorSettings = ReactorSettings())
        val operation = BatchCultivation(input)
        val timePoint = 0.0

        val result = operation.calculateSugarConcentration(timePoint)

        assertEquals(20.0, result)
    }

    @Test
    fun testProcessCanBeModeledOverTime() {
        val input = BatchCultivationInput(cultivationSettings = CultivationSettings(1.0,20.00, 0.12, 0.27, 0.00703, 0.4), reactorSettings = ReactorSettings())
        val operation = BatchCultivation(input)

        val expected = listOf(DataPoint(time=0.0, cellDensity=0.12, sugarConcentration=20.0), DataPoint(time=1.0, cellDensity=0.16,
            sugarConcentration=19.89), DataPoint(time=2.0, cellDensity=0.21, sugarConcentration=19.71),
            DataPoint(time=3.0, cellDensity=0.27, sugarConcentration=19.45), DataPoint(time=4.0, cellDensity=0.35, sugarConcentration=19.05),
            DataPoint(time=5.0, cellDensity=0.46, sugarConcentration=18.43), DataPoint(time=6.0, cellDensity=0.61, sugarConcentration=17.5),
            DataPoint(time=7.0, cellDensity=0.79, sugarConcentration=16.23), DataPoint(time=8.0, cellDensity=1.04, sugarConcentration=14.33),
            DataPoint(time=9.0, cellDensity=1.36, sugarConcentration=11.65), DataPoint(time=10.0, cellDensity=1.79, sugarConcentration=7.79),
            DataPoint(time=11.0, cellDensity=2.34, sugarConcentration=2.44), DataPoint(time=12.0, cellDensity=3.06, sugarConcentration=-5.04))

        val result = operation.modelOperation()

        assertEquals(expected, result)
    }

    @Test
    fun testProcessOutputCanBeGenerated() {
        val input = BatchCultivationInput(cultivationSettings = CultivationSettings(1.0,20.00, 0.12, 0.27, 0.00703, 0.4), reactorSettings = ReactorSettings())
        val operation = BatchCultivation(input)

        val expected = OperationOutput(duration = 15.57, model = listOf(DataPoint(time=0.0, cellDensity=0.12, sugarConcentration=20.0), DataPoint(time=1.0, cellDensity=0.16,
            sugarConcentration=19.89), DataPoint(time=2.0, cellDensity=0.21, sugarConcentration=19.71),
            DataPoint(time=3.0, cellDensity=0.27, sugarConcentration=19.45), DataPoint(time=4.0, cellDensity=0.35, sugarConcentration=19.05),
            DataPoint(time=5.0, cellDensity=0.46, sugarConcentration=18.43), DataPoint(time=6.0, cellDensity=0.61, sugarConcentration=17.5),
            DataPoint(time=7.0, cellDensity=0.79, sugarConcentration=16.23), DataPoint(time=8.0, cellDensity=1.04, sugarConcentration=14.33),
            DataPoint(time=9.0, cellDensity=1.36, sugarConcentration=11.65), DataPoint(time=10.0, cellDensity=1.79, sugarConcentration=7.79),
            DataPoint(time=11.0, cellDensity=2.34, sugarConcentration=2.44), DataPoint(time=12.0, cellDensity=3.06, sugarConcentration=-5.04)),
            costEstimation = CostEstimation(651.81), powerConsumption = PowerConsumption(4345.41)
        )

        val result = operation.generateOutput()

        assertEquals(expected, result)
    }

    @Test
    fun testMixingTimeCanBeCalculated() {
        val input = BatchCultivationInput(cultivationSettings = CultivationSettings(1.0,20.00, 0.12, 0.27, 0.00703, 0.4), reactorSettings = ReactorSettings())
        val operation = BatchCultivation(input)

        val result = operation.calculateMixingTime()

        assertEquals(50.36, result)
    }

    @Test
    fun testMixingTimeIsCalculatedBasedOnCirculationAtLowVolume() {
        val input = BatchCultivationInput(cultivationSettings = CultivationSettings(1.0,20.00, 0.12, 0.27, 0.00703, 0.4), reactorSettings = ReactorSettings())
        val operation = BatchCultivation(input)

        val result = operation.calculateMixingTime()

        assertEquals(85.22, result)
    }

    @Test
    fun testRelativePowerConsumptionCanBeCalculated() {
        val input = BatchCultivationInput(cultivationSettings = CultivationSettings(1.0,20.00, 0.12, 0.27, 0.00703, 0.4), reactorSettings = ReactorSettings())
        val operation = BatchCultivation(input)

        val result = operation.calculatePowerConsumptionWattsPerCube()

        assertEquals(5315.97, result)
    }

    @Test
    fun testTotalPowerConsumptionCanBeCalculated() {
        val input = BatchCultivationInput(cultivationSettings = CultivationSettings(1.0,20.00, 0.12, 0.27, 0.00703, 0.4), reactorSettings = ReactorSettings())
        val operation = BatchCultivation(input)

        val result = operation.calculatePowerConsumptionKWh()

        assertEquals(4345.41, result)
    }
}