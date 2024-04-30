package nl.sogyo.modelr

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import nl.sogyo.modelr.data.batchCultivationRequest.BatchCultivationInput
import nl.sogyo.modelr.data.batchCultivationRequest.CultivationSettings
import nl.sogyo.modelr.data.batchCultivationRequest.ReactorSettings
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

        val result = simulation.runSimulation().output[0]!!.duration

        assertEquals(17.51, result)
    }

    @Test
    fun testCreatingCultivationSettingsWithNegativeAccuracyThrowsIllegalArgumentException() {
        val factory = SimulationFactory()
        val settings = File("src/test/resources/simulationSettingsNeg.json").readText()

        assertThrows<IllegalArgumentException> {
            factory.createNewSimulation(listOf("batch-cultivation"), settings)
        }
    }

    @Test
    fun testCreatingCultivationSettingsWithNegativeCellDensityThrowsIllegalArgumentException() {
        val factory = SimulationFactory()
        val settings = """{
            "batchCultivation": {
            "costFactor": {
            "date": [
            2024,
            4,
            25
            ],
            "energy": 0.15,
            "id": 1
        },
            "id": 1,
            "impeller": {
            "id": 1,
            "impellerDiameter": 0.97,
            "impellerFlowNumber": 0.72,
            "impellerPowerNumber": 5.2,
            "type": "rushton turbine"
        },
            "microorganism": {
            "date": [
            2024,
            4,
            25
            ],
            "id": 1,
            "maintenance": 0.00703,
            "maxGrowthRate": 0.24,
            "name": "saccharomyces cerevisiae",
            "yield": 0.4
        },
            "position": 0,
            "reactor": {
            "date": [
            2024,
            4,
            25
            ],
            "height": 9.29,
            "id": 1,
            "name": "example",
            "nominalVolume": 70.0,
            "width": 3.1,
            "workingVolume": 52.5
        },
            "request": {
            "cultivationSettings": {
            "accuracy": 0.01,
            "id": 1,
            "initialCellDensity": -0.12,
            "initialSugarConcentration": 20.0,
            "maintenance": null,
            "maxGrowthRate": null,
            "microorganism": "saccharomyces cerevisiae",
            "yield": null
        },
            "id": 1,
            "operationType": "batch-cultivation",
            "reactorSettings": {
            "agitatorSpeed": 2.5,
            "height": null,
            "id": 1,
            "impellerType": "rushton turbine",
            "nominalVolume": null,
            "numberOfImpellers": 4,
            "reactorType": "example",
            "width": null,
            "workingVolume": null
        }
        },
            "result": null
        },
            "id": 1
        }"""

        assertThrows<IllegalArgumentException> {
            factory.createNewSimulation(listOf("batch-cultivation"), settings)
        }
    }

    @Test
    fun testCreatingCultivationSettingsWithNegativeSugarThrowsIllegalArgumentException() {
        val factory = SimulationFactory()
        val settings = """{
            "batchCultivation": {
            "costFactor": {
            "date": [
            2024,
            4,
            25
            ],
            "energy": 0.15,
            "id": 1
        },
            "id": 1,
            "impeller": {
            "id": 1,
            "impellerDiameter": 0.97,
            "impellerFlowNumber": 0.72,
            "impellerPowerNumber": 5.2,
            "type": "rushton turbine"
        },
            "microorganism": {
            "date": [
            2024,
            4,
            25
            ],
            "id": 1,
            "maintenance": 0.00703,
            "maxGrowthRate": 0.24,
            "name": "saccharomyces cerevisiae",
            "yield": 0.4
        },
            "position": 0,
            "reactor": {
            "date": [
            2024,
            4,
            25
            ],
            "height": 9.29,
            "id": 1,
            "name": "example",
            "nominalVolume": 70.0,
            "width": 3.1,
            "workingVolume": 52.5
        },
            "request": {
            "cultivationSettings": {
            "accuracy": 0.01,
            "id": 1,
            "initialCellDensity": 0.12,
            "initialSugarConcentration": -20.0,
            "maintenance": null,
            "maxGrowthRate": null,
            "microorganism": "saccharomyces cerevisiae",
            "yield": null
        },
            "id": 1,
            "operationType": "batch-cultivation",
            "reactorSettings": {
            "agitatorSpeed": 2.5,
            "height": null,
            "id": 1,
            "impellerType": "rushton turbine",
            "nominalVolume": null,
            "numberOfImpellers": 4,
            "reactorType": "example",
            "width": null,
            "workingVolume": null
        }
        },
            "result": null
        },
            "id": 1
        }"""


        assertThrows<IllegalArgumentException> {
            factory.createNewSimulation(listOf("batch-cultivation"), settings)
        }
    }

    @Test
    fun testCreatingCultivationSettingsWithNegativeMaintenanceThrowsIllegalArgumentException() {
        val factory = SimulationFactory()
        val settings = """{
            "batchCultivation": {
            "costFactor": {
            "date": [
            2024,
            4,
            25
            ],
            "energy": 0.15,
            "id": 1
        },
            "id": 1,
            "impeller": {
            "id": 1,
            "impellerDiameter": 0.97,
            "impellerFlowNumber": 0.72,
            "impellerPowerNumber": 5.2,
            "type": "rushton turbine"
        },
            "microorganism": {
            "date": [
            2024,
            4,
            25
            ],
            "id": 1,
            "maintenance": 0.00703,
            "maxGrowthRate": 0.24,
            "name": "saccharomyces cerevisiae",
            "yield": 0.4
        },
            "position": 0,
            "reactor": {
            "date": [
            2024,
            4,
            25
            ],
            "height": 9.29,
            "id": 1,
            "name": "example",
            "nominalVolume": 70.0,
            "width": 3.1,
            "workingVolume": 52.5
        },
            "request": {
            "cultivationSettings": {
            "accuracy": 0.01,
            "id": 1,
            "initialCellDensity": 0.12,
            "initialSugarConcentration": 20.0,
            "maintenance": -5.0,
            "maxGrowthRate": null,
            "microorganism": "saccharomyces cerevisiae",
            "yield": null
        },
            "id": 1,
            "operationType": "batch-cultivation",
            "reactorSettings": {
            "agitatorSpeed": 2.5,
            "height": null,
            "id": 1,
            "impellerType": "rushton turbine",
            "nominalVolume": null,
            "numberOfImpellers": 4,
            "reactorType": "example",
            "width": null,
            "workingVolume": null
        }
        },
            "result": null
        },
            "id": 1
        }"""


        assertThrows<IllegalArgumentException> {
            factory.createNewSimulation(listOf("batch-cultivation"), settings)
        }
    }

    @Test
    fun testCreatingCultivationSettingsWithNegativeGrowthThrowsIllegalArgumentException() {
        val factory = SimulationFactory()
        val settings = """{
            "batchCultivation": {
            "costFactor": {
            "date": [
            2024,
            4,
            25
            ],
            "energy": 0.15,
            "id": 1
        },
            "id": 1,
            "impeller": {
            "id": 1,
            "impellerDiameter": 0.97,
            "impellerFlowNumber": 0.72,
            "impellerPowerNumber": 5.2,
            "type": "rushton turbine"
        },
            "microorganism": {
            "date": [
            2024,
            4,
            25
            ],
            "id": 1,
            "maintenance": 0.00703,
            "maxGrowthRate": 0.24,
            "name": "saccharomyces cerevisiae",
            "yield": 0.4
        },
            "position": 0,
            "reactor": {
            "date": [
            2024,
            4,
            25
            ],
            "height": 9.29,
            "id": 1,
            "name": "example",
            "nominalVolume": 70.0,
            "width": 3.1,
            "workingVolume": 52.5
        },
            "request": {
            "cultivationSettings": {
            "accuracy": 0.01,
            "id": 1,
            "initialCellDensity": 0.12,
            "initialSugarConcentration": 20.0,
            "maintenance": null,
            "maxGrowthRate": -5.0,
            "microorganism": "saccharomyces cerevisiae",
            "yield": null
        },
            "id": 1,
            "operationType": "batch-cultivation",
            "reactorSettings": {
            "agitatorSpeed": 2.5,
            "height": null,
            "id": 1,
            "impellerType": "rushton turbine",
            "nominalVolume": null,
            "numberOfImpellers": 4,
            "reactorType": "example",
            "width": null,
            "workingVolume": null
        }
        },
            "result": null
        },
            "id": 1
        }"""


        assertThrows<IllegalArgumentException> {
            factory.createNewSimulation(listOf("batch-cultivation"), settings)
        }
    }

    @Test
    fun testCreatingCultivationSettingsWithNegativeYieldThrowsIllegalArgumentException() {
        val factory = SimulationFactory()
        val settings = """{
            "batchCultivation": {
            "costFactor": {
            "date": [
            2024,
            4,
            25
            ],
            "energy": 0.15,
            "id": 1
        },
            "id": 1,
            "impeller": {
            "id": 1,
            "impellerDiameter": 0.97,
            "impellerFlowNumber": 0.72,
            "impellerPowerNumber": 5.2,
            "type": "rushton turbine"
        },
            "microorganism": {
            "date": [
            2024,
            4,
            25
            ],
            "id": 1,
            "maintenance": 0.00703,
            "maxGrowthRate": 0.24,
            "name": "saccharomyces cerevisiae",
            "yield": 0.4
        },
            "position": 0,
            "reactor": {
            "date": [
            2024,
            4,
            25
            ],
            "height": 9.29,
            "id": 1,
            "name": "example",
            "nominalVolume": 70.0,
            "width": 3.1,
            "workingVolume": 52.5
        },
            "request": {
            "cultivationSettings": {
            "accuracy": 0.01,
            "id": 1,
            "initialCellDensity": 0.12,
            "initialSugarConcentration": 20.0,
            "maintenance": null,
            "maxGrowthRate": null,
            "microorganism": "saccharomyces cerevisiae",
            "yield": -5.0
        },
            "id": 1,
            "operationType": "batch-cultivation",
            "reactorSettings": {
            "agitatorSpeed": 2.5,
            "height": null,
            "id": 1,
            "impellerType": "rushton turbine",
            "nominalVolume": null,
            "numberOfImpellers": 4,
            "reactorType": "example",
            "width": null,
            "workingVolume": null
        }
        },
            "result": null
        },
            "id": 1
        }"""


        assertThrows<IllegalArgumentException> {
            factory.createNewSimulation(listOf("batch-cultivation"), settings)
        }
    }

    @Test
    fun testCreatingReactorSettingsWithNegativeAgitatorSpeedThrowsIllegalArgumentException() {
        val factory = SimulationFactory()
        val settings = """{
            "batchCultivation": {
            "costFactor": {
            "date": [
            2024,
            4,
            25
            ],
            "energy": 0.15,
            "id": 1
        },
            "id": 1,
            "impeller": {
            "id": 1,
            "impellerDiameter": 0.97,
            "impellerFlowNumber": 0.72,
            "impellerPowerNumber": 5.2,
            "type": "rushton turbine"
        },
            "microorganism": {
            "date": [
            2024,
            4,
            25
            ],
            "id": 1,
            "maintenance": 0.00703,
            "maxGrowthRate": 0.24,
            "name": "saccharomyces cerevisiae",
            "yield": 0.4
        },
            "position": 0,
            "reactor": {
            "date": [
            2024,
            4,
            25
            ],
            "height": 9.29,
            "id": 1,
            "name": "example",
            "nominalVolume": 70.0,
            "width": 3.1,
            "workingVolume": 52.5
        },
            "request": {
            "cultivationSettings": {
            "accuracy": 0.01,
            "id": 1,
            "initialCellDensity": 0.12,
            "initialSugarConcentration": 20.0,
            "maintenance": null,
            "maxGrowthRate": null,
            "microorganism": "saccharomyces cerevisiae",
            "yield": null
        },
            "id": 1,
            "operationType": "batch-cultivation",
            "reactorSettings": {
            "agitatorSpeed": -2.5,
            "height": null,
            "id": 1,
            "impellerType": "rushton turbine",
            "nominalVolume": null,
            "numberOfImpellers": 4,
            "reactorType": "example",
            "width": null,
            "workingVolume": null
        }
        },
            "result": null
        },
            "id": 1
        }"""

        assertThrows<IllegalArgumentException> {
            factory.createNewSimulation(listOf("batch-cultivation"), settings)
        }
    }

    @Test
    fun testCreatingReactorSettingsWithNegativeHeightThrowsIllegalArgumentException() {
        val factory = SimulationFactory()
        val settings = """{
            "batchCultivation": {
            "costFactor": {
            "date": [
            2024,
            4,
            25
            ],
            "energy": 0.15,
            "id": 1
        },
            "id": 1,
            "impeller": {
            "id": 1,
            "impellerDiameter": 0.97,
            "impellerFlowNumber": 0.72,
            "impellerPowerNumber": 5.2,
            "type": "rushton turbine"
        },
            "microorganism": {
            "date": [
            2024,
            4,
            25
            ],
            "id": 1,
            "maintenance": 0.00703,
            "maxGrowthRate": 0.24,
            "name": "saccharomyces cerevisiae",
            "yield": 0.4
        },
            "position": 0,
            "reactor": {
            "date": [
            2024,
            4,
            25
            ],
            "height": 9.29,
            "id": 1,
            "name": "example",
            "nominalVolume": 70.0,
            "width": 3.1,
            "workingVolume": 52.5
        },
            "request": {
            "cultivationSettings": {
            "accuracy": 0.01,
            "id": 1,
            "initialCellDensity": 0.12,
            "initialSugarConcentration": 20.0,
            "maintenance": null,
            "maxGrowthRate": null,
            "microorganism": "saccharomyces cerevisiae",
            "yield": null
        },
            "id": 1,
            "operationType": "batch-cultivation",
            "reactorSettings": {
            "agitatorSpeed": 2.5,
            "height": -5.0,
            "id": 1,
            "impellerType": "rushton turbine",
            "nominalVolume": null,
            "numberOfImpellers": 4,
            "reactorType": "example",
            "width": null,
            "workingVolume": null
        }
        },
            "result": null
        },
            "id": 1
        }"""

        assertThrows<IllegalArgumentException> {
            factory.createNewSimulation(listOf("batch-cultivation"), settings)
        }
    }

    @Test
    fun testCreatingReactorSettingsWithNegativeNomVolumeThrowsIllegalArgumentException() {
        val factory = SimulationFactory()
        val settings = """{
            "batchCultivation": {
            "costFactor": {
            "date": [
            2024,
            4,
            25
            ],
            "energy": 0.15,
            "id": 1
        },
            "id": 1,
            "impeller": {
            "id": 1,
            "impellerDiameter": 0.97,
            "impellerFlowNumber": 0.72,
            "impellerPowerNumber": 5.2,
            "type": "rushton turbine"
        },
            "microorganism": {
            "date": [
            2024,
            4,
            25
            ],
            "id": 1,
            "maintenance": 0.00703,
            "maxGrowthRate": 0.24,
            "name": "saccharomyces cerevisiae",
            "yield": 0.4
        },
            "position": 0,
            "reactor": {
            "date": [
            2024,
            4,
            25
            ],
            "height": 9.29,
            "id": 1,
            "name": "example",
            "nominalVolume": 70.0,
            "width": 3.1,
            "workingVolume": 52.5
        },
            "request": {
            "cultivationSettings": {
            "accuracy": 0.01,
            "id": 1,
            "initialCellDensity": 0.12,
            "initialSugarConcentration": 20.0,
            "maintenance": null,
            "maxGrowthRate": null,
            "microorganism": "saccharomyces cerevisiae",
            "yield": null
        },
            "id": 1,
            "operationType": "batch-cultivation",
            "reactorSettings": {
            "agitatorSpeed": 2.5,
            "height": null,
            "id": 1,
            "impellerType": "rushton turbine",
            "nominalVolume": -5.0,
            "numberOfImpellers": 4,
            "reactorType": "example",
            "width": null,
            "workingVolume": null
        }
        },
            "result": null
        },
            "id": 1
        }"""

        assertThrows<IllegalArgumentException> {
            factory.createNewSimulation(listOf("batch-cultivation"), settings)
        }
    }

    @Test
    fun testCreatingReactorSettingsWithNegativeNrImpellersThrowsIllegalArgumentException() {
        val factory = SimulationFactory()
        val settings = """{
            "batchCultivation": {
            "costFactor": {
            "date": [
            2024,
            4,
            25
            ],
            "energy": 0.15,
            "id": 1
        },
            "id": 1,
            "impeller": {
            "id": 1,
            "impellerDiameter": 0.97,
            "impellerFlowNumber": 0.72,
            "impellerPowerNumber": 5.2,
            "type": "rushton turbine"
        },
            "microorganism": {
            "date": [
            2024,
            4,
            25
            ],
            "id": 1,
            "maintenance": 0.00703,
            "maxGrowthRate": 0.24,
            "name": "saccharomyces cerevisiae",
            "yield": 0.4
        },
            "position": 0,
            "reactor": {
            "date": [
            2024,
            4,
            25
            ],
            "height": 9.29,
            "id": 1,
            "name": "example",
            "nominalVolume": 70.0,
            "width": 3.1,
            "workingVolume": 52.5
        },
            "request": {
            "cultivationSettings": {
            "accuracy": 0.01,
            "id": 1,
            "initialCellDensity": 0.12,
            "initialSugarConcentration": 20.0,
            "maintenance": null,
            "maxGrowthRate": null,
            "microorganism": "saccharomyces cerevisiae",
            "yield": null
        },
            "id": 1,
            "operationType": "batch-cultivation",
            "reactorSettings": {
            "agitatorSpeed": 2.5,
            "height": 5.0,
            "id": 1,
            "impellerType": "rushton turbine",
            "nominalVolume": null,
            "numberOfImpellers": -4,
            "reactorType": "example",
            "width": null,
            "workingVolume": null
        }
        },
            "result": null
        },
            "id": 1
        }"""

        assertThrows<IllegalArgumentException> {
            factory.createNewSimulation(listOf("batch-cultivation"), settings)
        }
    }


    @Test
    fun testCreatingReactorSettingsWithNegativeWidthThrowsIllegalArgumentException() {
        val factory = SimulationFactory()
        val settings = """{
            "batchCultivation": {
            "costFactor": {
            "date": [
            2024,
            4,
            25
            ],
            "energy": 0.15,
            "id": 1
        },
            "id": 1,
            "impeller": {
            "id": 1,
            "impellerDiameter": 0.97,
            "impellerFlowNumber": 0.72,
            "impellerPowerNumber": 5.2,
            "type": "rushton turbine"
        },
            "microorganism": {
            "date": [
            2024,
            4,
            25
            ],
            "id": 1,
            "maintenance": 0.00703,
            "maxGrowthRate": 0.24,
            "name": "saccharomyces cerevisiae",
            "yield": 0.4
        },
            "position": 0,
            "reactor": {
            "date": [
            2024,
            4,
            25
            ],
            "height": 9.29,
            "id": 1,
            "name": "example",
            "nominalVolume": 70.0,
            "width": 3.1,
            "workingVolume": 52.5
        },
            "request": {
            "cultivationSettings": {
            "accuracy": 0.01,
            "id": 1,
            "initialCellDensity": 0.12,
            "initialSugarConcentration": 20.0,
            "maintenance": null,
            "maxGrowthRate": null,
            "microorganism": "saccharomyces cerevisiae",
            "yield": null
        },
            "id": 1,
            "operationType": "batch-cultivation",
            "reactorSettings": {
            "agitatorSpeed": 2.5,
            "height": 5.0,
            "id": 1,
            "impellerType": "rushton turbine",
            "nominalVolume": null,
            "numberOfImpellers": 4,
            "reactorType": "example",
            "width": -5.0,
            "workingVolume": null
        }
        },
            "result": null
        },
            "id": 1
        }"""

        assertThrows<IllegalArgumentException> {
            factory.createNewSimulation(listOf("batch-cultivation"), settings)
        }
    }

    @Test
    fun testCreatingReactorSettingsWithNegativeWorkVolumeThrowsIllegalArgumentException() {
        val factory = SimulationFactory()
        val settings = """{
            "batchCultivation": {
            "costFactor": {
            "date": [
            2024,
            4,
            25
            ],
            "energy": 0.15,
            "id": 1
        },
            "id": 1,
            "impeller": {
            "id": 1,
            "impellerDiameter": 0.97,
            "impellerFlowNumber": 0.72,
            "impellerPowerNumber": 5.2,
            "type": "rushton turbine"
        },
            "microorganism": {
            "date": [
            2024,
            4,
            25
            ],
            "id": 1,
            "maintenance": 0.00703,
            "maxGrowthRate": 0.24,
            "name": "saccharomyces cerevisiae",
            "yield": 0.4
        },
            "position": 0,
            "reactor": {
            "date": [
            2024,
            4,
            25
            ],
            "height": 9.29,
            "id": 1,
            "name": "example",
            "nominalVolume": 70.0,
            "width": 3.1,
            "workingVolume": 52.5
        },
            "request": {
            "cultivationSettings": {
            "accuracy": 0.01,
            "id": 1,
            "initialCellDensity": 0.12,
            "initialSugarConcentration": 20.0,
            "maintenance": null,
            "maxGrowthRate": null,
            "microorganism": "saccharomyces cerevisiae",
            "yield": null
        },
            "id": 1,
            "operationType": "batch-cultivation",
            "reactorSettings": {
            "agitatorSpeed": 2.5,
            "height": 5.0,
            "id": 1,
            "impellerType": "rushton turbine",
            "nominalVolume": null,
            "numberOfImpellers": 4,
            "reactorType": "example",
            "width": null,
            "workingVolume": -5.0
        }
        },
            "result": null
        },
            "id": 1
        }"""

        assertThrows<IllegalArgumentException> {
            factory.createNewSimulation(listOf("batch-cultivation"), settings)
        }
    }


}