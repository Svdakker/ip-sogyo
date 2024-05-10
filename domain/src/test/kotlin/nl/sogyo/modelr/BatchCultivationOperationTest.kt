package nl.sogyo.modelr

import nl.sogyo.modelr.data.*
import nl.sogyo.modelr.data.batchCultivationRequest.BatchCultivationInput
import nl.sogyo.modelr.data.batchCultivationRequest.CultivationSettings
import nl.sogyo.modelr.data.batchCultivationRequest.ReactorSettings
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class BatchCultivationOperationTest {

    @Test
    fun testCalculateDuration() {
        val input = BatchCultivationInput(cultivationSettings = CultivationSettings(1.0,20.00, 0.12, 0.27, 0.00703, 0.4), reactorSettings = ReactorSettings(nominalVolume = 70.0, workingVolume = 52.5, height = 9.29, width = 3.10, impellerType = "rushton turbine", numberOfImpellers = 4, agitatorSpeed = 2.5, impellerDiameter = 0.97, impellerFlowNumber = 0.72, impellerPowerNumber = 5.2))
        val operation = BatchCultivationOperation(input)

        val result = operation.calculateDuration()

        assertEquals(15.57, result)
    }

    @Test
    fun testCalculateCellDensity() {
        val input = BatchCultivationInput(cultivationSettings = CultivationSettings(1.0,20.00, 0.12, 0.27, 0.00703, 0.4), reactorSettings = ReactorSettings(nominalVolume = 70.0, workingVolume = 52.5, height = 9.29, width = 3.10, impellerType = "rushton turbine", numberOfImpellers = 4, agitatorSpeed = 2.5, impellerDiameter = 0.97, impellerFlowNumber = 0.72, impellerPowerNumber = 5.2))
        val operation = BatchCultivationOperation(input)
        val timePoint = 10.00 //h

        val result = operation.calculateCellDensity(timePoint)

        assertEquals(1.79, result)
    }

    @Test
    fun testCalculateCellDensityAtTime0() {
        val input = BatchCultivationInput(cultivationSettings = CultivationSettings(1.0,20.00, 0.12, 0.27, 0.00703, 0.4), reactorSettings = ReactorSettings(nominalVolume = 70.0, workingVolume = 52.5, height = 9.29, width = 3.10, impellerType = "rushton turbine", numberOfImpellers = 4, agitatorSpeed = 2.5, impellerDiameter = 0.97, impellerFlowNumber = 0.72, impellerPowerNumber = 5.2))
        val operation = BatchCultivationOperation(input)
        val timePoint = 0.0 //h

        val result = operation.calculateCellDensity(timePoint)

        assertEquals(0.12, result)
    }

    @Test
    fun testCalculateFinalCellDensity() {
        val input = BatchCultivationInput(cultivationSettings = CultivationSettings(1.0,20.00, 0.12, 0.27, 0.00703, 0.4), reactorSettings = ReactorSettings(nominalVolume = 70.0, workingVolume = 52.5, height = 9.29, width = 3.10, impellerType = "rushton turbine", numberOfImpellers = 4, agitatorSpeed = 2.5, impellerDiameter = 0.97, impellerFlowNumber = 0.72, impellerPowerNumber = 5.2))
        val operation = BatchCultivationOperation(input)

        val result = operation.calculateFinalCellDensity()

        assertEquals(8.037540284151723, result)
    }

    @Test
    fun testCalculateSpecificSugarUptakeRate() {
        val input = BatchCultivationInput(cultivationSettings = CultivationSettings(1.0,20.00, 0.12, 0.27, 0.00703, 0.4), reactorSettings = ReactorSettings(nominalVolume = 70.0, workingVolume = 52.5, height = 9.29, width = 3.10, impellerType = "rushton turbine", numberOfImpellers = 4, agitatorSpeed = 2.5, impellerDiameter = 0.97, impellerFlowNumber = 0.72, impellerPowerNumber = 5.2))
        val operation = BatchCultivationOperation(input)

        val result = operation.calculateSugarUptakeRate()

        assertEquals(0.68203, result)
    }

    @Test
    fun testCalculateSugarConcentration() {
        val input = BatchCultivationInput(cultivationSettings = CultivationSettings(1.0,20.00, 0.12, 0.27, 0.00703, 0.4), reactorSettings = ReactorSettings(nominalVolume = 70.0, workingVolume = 52.5, height = 9.29, width = 3.10, impellerType = "rushton turbine", numberOfImpellers = 4, agitatorSpeed = 2.5, impellerDiameter = 0.97, impellerFlowNumber = 0.72, impellerPowerNumber = 5.2))
        val operation = BatchCultivationOperation(input)
        val timePoint = 10.0

        val result = operation.calculateSugarConcentration(timePoint)

        assertEquals(7.79, result)
    }

    @Test
    fun testCalculateSugarConcentrationAtTime0() {
        val input = BatchCultivationInput(cultivationSettings = CultivationSettings(1.0,20.00, 0.12, 0.27, 0.00703, 0.4), reactorSettings = ReactorSettings(nominalVolume = 70.0, workingVolume = 52.5, height = 9.29, width = 3.10, impellerType = "rushton turbine", numberOfImpellers = 4, agitatorSpeed = 2.5, impellerDiameter = 0.97, impellerFlowNumber = 0.72, impellerPowerNumber = 5.2))
        val operation = BatchCultivationOperation(input)
        val timePoint = 0.0

        val result = operation.calculateSugarConcentration(timePoint)

        assertEquals(20.0, result)
    }

    @Test
    fun testProcessCanBeModeledOverTime() {
        val input = BatchCultivationInput(cultivationSettings = CultivationSettings(1.0,20.00, 0.12, 0.27, 0.00703, 0.4), reactorSettings = ReactorSettings(nominalVolume = 70.0, workingVolume = 52.5, height = 9.29, width = 3.10, impellerType = "rushton turbine", numberOfImpellers = 4, agitatorSpeed = 2.5, impellerDiameter = 0.97, impellerFlowNumber = 0.72, impellerPowerNumber = 5.2))
        val operation = BatchCultivationOperation(input)

        val expected = listOf(
            listOf(0.0, 0.12, 20.0), listOf(1.0, .16, 19.89), listOf(2.0, 0.21, 19.71), listOf(3.0, 0.27, 19.45),
            listOf(4.0, 0.35, 19.05), listOf(5.0, 0.46, 18.43), listOf(6.0, 0.61, 17.5), listOf(7.0, 0.79, 16.23),
            listOf(8.0, 1.04, 14.33), listOf(9.0, 1.36, 11.65), listOf(10.0, 1.79, 7.79), listOf(11.0, 2.34, 2.44),
            listOf(12.0, 3.06, -5.04)
        )

        val result = operation.modelOperation()

        assertEquals(expected, result)
    }

    @Test
    fun testProcessOutputCanBeGenerated() {
        val input = BatchCultivationInput(cultivationSettings = CultivationSettings(1.0,20.00, 0.12, 0.27, 0.00703, 0.4), reactorSettings = ReactorSettings(nominalVolume = 70.0, workingVolume = 52.5, height = 9.29, width = 3.10, impellerType = "rushton turbine", numberOfImpellers = 4, agitatorSpeed = 2.5, impellerDiameter = 0.97, impellerFlowNumber = 0.72, impellerPowerNumber = 5.2))
        val operation = BatchCultivationOperation(input)

        val expected = OperationOutput(duration = 15.57, model = listOf(
            listOf(0.0, 0.12, 20.0), listOf(1.0, .16, 19.89), listOf(2.0, 0.21, 19.71), listOf(3.0, 0.27, 19.45),
            listOf(4.0, 0.35, 19.05), listOf(5.0, 0.46, 18.43), listOf(6.0, 0.61, 17.5), listOf(7.0, 0.79, 16.23),
            listOf(8.0, 1.04, 14.33), listOf(9.0, 1.36, 11.65), listOf(10.0, 1.79, 7.79), listOf(11.0, 2.34, 2.44),
            listOf(12.0, 3.06, -5.04)),
            costEstimation = CostEstimation(651.81), powerConsumption = PowerConsumption(4345.41)
        )

        val result = operation.generateOutput()

        assertEquals(expected, result)
    }

    @Test
    fun testMixingTimeCanBeCalculated() {
        val input = BatchCultivationInput(cultivationSettings = CultivationSettings(1.0,20.00, 0.12, 0.27, 0.00703, 0.4), reactorSettings = ReactorSettings(nominalVolume = 70.0, workingVolume = 52.5, height = 9.29, width = 3.10, impellerType = "rushton turbine", numberOfImpellers = 4, agitatorSpeed = 2.5, impellerDiameter = 0.97, impellerFlowNumber = 0.72, impellerPowerNumber = 5.2))
        val operation = BatchCultivationOperation(input)

        val result = operation.calculateMixingTime()

        assertEquals(50.36, result)
    }

    @Test
    fun testMixingTimeIsCalculatedBasedOnCirculationAtLowVolume() {
        val input = BatchCultivationInput(cultivationSettings = CultivationSettings(1.0,20.00, 0.12, 0.27, 0.00703, 0.4), reactorSettings = ReactorSettings(nominalVolume = 50.0, workingVolume = 35.0, height = 9.29, width = 3.10, impellerType = "rushton turbine", numberOfImpellers = 4, agitatorSpeed = 2.5, impellerDiameter = 0.97, impellerFlowNumber = 0.72, impellerPowerNumber = 5.2))
        val operation = BatchCultivationOperation(input)

        val result = operation.calculateMixingTime()

        assertEquals(85.22, result)
    }

    @Test
    fun testRelativePowerConsumptionCanBeCalculated() {
        val input = BatchCultivationInput(cultivationSettings = CultivationSettings(1.0,20.00, 0.12, 0.27, 0.00703, 0.4), reactorSettings = ReactorSettings(nominalVolume = 70.0, workingVolume = 52.5, height = 9.29, width = 3.10, impellerType = "rushton turbine", numberOfImpellers = 4, agitatorSpeed = 2.5, impellerDiameter = 0.97, impellerFlowNumber = 0.72, impellerPowerNumber = 5.2))
        val operation = BatchCultivationOperation(input)

        val result = operation.calculatePowerConsumptionWattsPerCube()

        assertEquals(5315.97, result)
    }

    @Test
    fun testTotalPowerConsumptionCanBeCalculated() {
        val input = BatchCultivationInput(cultivationSettings = CultivationSettings(1.0,20.00, 0.12, 0.27, 0.00703, 0.4), reactorSettings = ReactorSettings(nominalVolume = 70.0, workingVolume = 52.5, height = 9.29, width = 3.10, impellerType = "rushton turbine", numberOfImpellers = 4, agitatorSpeed = 2.5, impellerDiameter = 0.97, impellerFlowNumber = 0.72, impellerPowerNumber = 5.2))
        val operation = BatchCultivationOperation(input)

        val result = operation.calculatePowerConsumptionKWh()

        assertEquals(4345.41, result)
    }
}