package nl.sogyo.modelr

import nl.sogyo.modelr.data.CostEstimation
import nl.sogyo.modelr.data.OperationOutput
import nl.sogyo.modelr.data.PowerConsumption
import nl.sogyo.modelr.data.SimulationOutput
import nl.sogyo.modelr.data.batchCultivationRequest.BatchCultivationInput
import nl.sogyo.modelr.data.batchCultivationRequest.CultivationSettings
import nl.sogyo.modelr.data.batchCultivationRequest.ReactorSettings
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.io.File

class SimulationTest {

    @Test
    fun testSimulationCanBeCreated() {
        val simulation = Simulation(BatchCultivationOperation(BatchCultivationInput(cultivationSettings = CultivationSettings(1.0,20.00, 0.12, 0.27, 0.00703, 0.4), reactorSettings = ReactorSettings(nominalVolume = 70.0, workingVolume = 52.5, height = 9.29, width = 3.10, impellerType = "rushton turbine", numberOfImpellers = 4, agitatorSpeed = 2.5))))

        assertNotNull(simulation)
    }

    @Test
    fun testSimulationStartsWithUnitOperation() {
        val simulation = Simulation(BatchCultivationOperation(BatchCultivationInput(cultivationSettings = CultivationSettings(1.0,20.00, 0.12, 0.27, 0.00703, 0.4), reactorSettings = ReactorSettings(nominalVolume = 70.0, workingVolume = 52.5, height = 9.29, width = 3.10, impellerType = "rushton turbine", numberOfImpellers = 4, agitatorSpeed = 2.5))))

        assertNotNull(simulation.getFirstUnitOperation())
    }

    @Test
    fun testRunSimulationReturnsDuration() {
        val factory = SimulationFactory()
        val settings = File("src/test/resources/simulationSettings.json").readText()
        val simulation = factory.createNewSimulation(listOf("batch-cultivation"),settings)

        val result = simulation.runSimulation().output[0]!!.duration

        assertEquals(17.51, result)
    }

    @Test
    fun testUserInputHasPriorityOverDefault() {
        val factory = SimulationFactory()
        val operations = listOf("batch-cultivation", "centrifugation")
        val settings = File("src/test/resources/simulationSettings2.json").readText()
        val simulation = factory.createNewSimulation(operations,settings)
        val expectedModel = listOf(listOf(0.0, 0.12, 20.0), listOf(1.0, 0.2, 19.87), listOf(2.0, 0.33, 19.55), listOf(3.0, 0.54, 18.91),
            listOf(4.0, 0.89, 17.6), listOf(5.0, 1.46, 15.07), listOf(6.0, 2.41, 10.24), listOf(7.0, 3.97, 1.24), listOf(8.0, 6.55, -15.37))

        val result = simulation.runSimulation()

        assertEquals(9.65, result.output[0]!!.duration)
        assertEquals(403.98, result.output[0]!!.costEstimation.energy)
        assertEquals(2693.2, result.output[0]!!.powerConsumption.operations)
        assertEquals(expectedModel, result.output[0]!!.model)
        assertEquals(5701.28, result.output[1]!!.duration)
    }

    @Test
    fun testSimulationCanHaveTwoDifferentBatchCultivations() {
        val factory = SimulationFactory()
        val operations = listOf("batch-cultivation", "batch-cultivation")
        val settings = File("src/test/resources/simulationSettingsTwoBatch.json").readText()
        val simulation = factory.createNewSimulation(operations,settings)

        val firstBatch = simulation.getFirstUnitOperation()
        val secondBatch = simulation.getFirstUnitOperation().getNextOperation()

        assertNotEquals(firstBatch.generateOutput(), secondBatch!!.generateOutput())
    }

    @Test
    fun testSimulationCanHaveThreeDifferentBatchCultivations() {
        val factory = SimulationFactory()
        val operations = listOf("batch-cultivation", "batch-cultivation", "batch-cultivation")
        val settings = File("src/test/resources/simulationSettingsThreeBatch.json").readText()
        val simulation = factory.createNewSimulation(operations,settings)

        val firstBatch = simulation.getFirstUnitOperation()
        val secondBatch = firstBatch.getNextOperation()
        val thirdBatch = secondBatch!!.getNextOperation()

        assertNotEquals(firstBatch.generateOutput(), secondBatch.generateOutput())
        assertNotEquals(firstBatch.generateOutput(), thirdBatch!!.generateOutput())
        assertNotEquals(secondBatch.generateOutput(), thirdBatch.generateOutput())
    }

    @Test
    fun testSubsequentCultivationsUseOutputFromPreviousCultivations() {
        val factory = SimulationFactory()
        val operations = listOf("batch-cultivation", "batch-cultivation", "batch-cultivation")
        val settings = File("src/test/resources/simulationSettingsThreeBatch.json").readText()
        val simulation = factory.createNewSimulation(operations,settings)
        val expected = SimulationOutput(listOf(OperationOutput(17.51, listOf(listOf(0.0, 0.12, 20.0), listOf(5.0, 0.4, 18.79), listOf(10.0, 1.32, 11.99), listOf(15.0, 4.39, -19.97)), CostEstimation(733.03), PowerConsumption(4886.84)),
            OperationOutput(8.23, listOf(listOf(0.0, 0.28, 22.51), listOf(5.0, 3.37, 11.14), listOf(10.0, 41.07, -254.71)), CostEstimation(344.54), PowerConsumption(2296.9)),
            OperationOutput(3.46, listOf(listOf(0.0, 3.37, 21.14), listOf(5.0, 41.06, -117.44)), CostEstimation(energy=144.85), PowerConsumption(operations=965.65))))
        val result = simulation.runSimulation()

        assertEquals(expected, result)
    }

    @Test
    fun testCentrifugationCanBeRunAfterBatchCultivationWithPreviousOutput() {
        val factory = SimulationFactory()
        val operations = listOf("batch-cultivation", "centrifugation")
        val settings = File("src/test/resources/simulationSettingsCentrifuge.json").readText()
        val simulation = factory.createNewSimulation(operations,settings)

        val expected = SimulationOutput(listOf(OperationOutput(17.51, listOf(listOf(0.0, 0.12, 20.0), listOf(5.0, 0.4, 18.79), listOf(10.0, 1.32, 11.99), listOf(15.0, 4.39, -19.97)), CostEstimation(733.03), PowerConsumption(4886.84)),
                OperationOutput(duration=452.9, model=listOf(listOf(1.2879999999999999E-5, 1585591.8, 1132.25, 5661.25, 849.19), listOf(1.932E-5, 1057061.2, 754.83, 3774.15, 566.12), listOf(2.5759999999999997E-5, 792795.9, 566.12, 2830.6, 424.59), listOf(3.22E-5, 634236.72, 452.9, 2264.5, 339.68), listOf(3.8639999999999996E-5, 528530.6, 377.42, 1887.1, 283.07), listOf(4.5079999999999995E-5, 453026.23, 323.5, 1617.5, 242.63), listOf(5.1519999999999995E-5, 396397.95, 283.06, 1415.3, 212.3)), costEstimation=CostEstimation(energy=339.68), powerConsumption=PowerConsumption(operations=2264.5))))
        val result = simulation.runSimulation()

        assertEquals(expected, result)
    }

    @Test
    fun testCentrifugationCantBeRunBeforeBatchCultivation() {
        val factory = SimulationFactory()
        val operations = listOf("centrifugation", "batch-cultivation")
        val settings = File("src/test/resources/simulationSettingsCentrifuge2.json").readText()
        val simulation = factory.createNewSimulation(operations,settings)

        assertThrows<IllegalArgumentException> {
            simulation.runSimulation()
        }
    }
}