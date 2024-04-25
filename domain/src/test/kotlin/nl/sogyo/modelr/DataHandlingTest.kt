package nl.sogyo.modelr

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import nl.sogyo.modelr.data.BatchCultivationInput
import nl.sogyo.modelr.data.CultivationSettings
import nl.sogyo.modelr.data.ReactorSettings
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.io.File

class DataHandlingTest {

    @Test
    fun testInitializationOfBatchCultivationInputDataClass() {
        val settings = """{"operationType":"batch-cultivation","cultivationSettings":{"accuracy":"1.0","initialSugarConcentration":"20.0","initialCellDensity":"0.12","maxGrowthRate":"0.27","maintenance":"0.00703","yield":"0.4"},"reactorSettings":{"nominalVolume":"70.0","workingVolume":"52.5","height":"9.29","width":"3.10","impellerType":"rushton turbine","numberOfImpellers":"4","agitatorSpeed":"2.5"}}"""
        val expected = BatchCultivationInput(cultivationSettings = CultivationSettings(1.0,20.00, 0.12, 0.27, 0.00703, 0.4), reactorSettings = ReactorSettings(nominalVolume = 70.0, workingVolume = 52.5, height = 9.29, width = 3.10, impellerType = "rushton turbine", numberOfImpellers = 4, agitatorSpeed = 2.5))

        val objectMapper = jacksonObjectMapper()

        val result = objectMapper.readValue<BatchCultivationInput>(settings)

        assertEquals(expected, result)
    }

    @Test
    fun testRunSimulationWithJson() {
        val settings = File("src/test/resources/simulationSettings.json").readText()
        val factory = SimulationFactory()
        val simulation = factory.createNewSimulation(listOf("batch-cultivation"), settings)

        val result = simulation.runSimulation().duration

        assertEquals(17.51, result)
    }

    @Test
    fun testCreatingCultivationSettingsWithNegativeValuesThrowsIllegalArgumentException() {
        val factory = SimulationFactory()
        val settings = File("src/test/resources/simulationSettingsNeg.json").readText()

        assertThrows<IllegalArgumentException> {
            factory.createNewSimulation(listOf("batch-cultivation"), settings)
        }
    }
}