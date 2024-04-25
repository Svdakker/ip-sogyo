package nl.sogyo.modelr

import nl.sogyo.modelr.data.*
import nl.sogyo.modelr.data.batchCultivationRequest.BatchCultivationInput
import nl.sogyo.modelr.data.batchCultivationRequest.CostFactors
import kotlin.math.exp
import kotlin.math.ln
import kotlin.math.log
import kotlin.math.pow

class BatchCultivationCalc(private val input: BatchCultivationInput, private val costs: CostFactors = CostFactors()) : UnitOperation() {

    /**
     * Calculations to model a batch cultivation over time
     *
     * Calculations are now based on an ethanol production process from glucose, by S.cerevisiae. In future releases,
     * the user should be able to choose microorganisms, substrates and desired products
     * Process taken from: Design of a batch stirred fermenter for ethanol production, Mohammad Emal Qazizada
     */

    override fun modelOperation(): List<DataPoint> {
        val model = mutableListOf<DataPoint>()
        modelDataPoints(model,0.0)
        return model
    }

    private fun modelDataPoints(model: MutableList<DataPoint>, time: Double) {
        if (calculateSugarConcentration(time) >= 0) {
            model.add(calculateDataPoint(time))
            modelDataPoints(model,time + input.cultivationSettings.accuracy)
        } else {
            model.add(calculateDataPoint(time))
        }
    }

    private fun calculateDataPoint(timePoint: Double): DataPoint {
        return DataPoint(timePoint, calculateCellDensity(timePoint), calculateSugarConcentration(timePoint))
    }

    override fun calculateEnergyConsumption(): PowerConsumption {
        return PowerConsumption(calculatePowerConsumptionKWh())
    }

    override fun calculateCosts(): CostEstimation {
        val energyCosts = round(multiply(calculatePowerConsumptionKWh(), costs.energy))
        return CostEstimation(energyCosts)
    }

    /**
     * Calculations to model the growth and production/consumption inside the reactor
     *
     * Assume Cs >> Ks, so maximum growth rate and sugar consumption
     * Assume reactor is ideally mixed
     */

    override fun calculateDuration(): Double {
        return round(divide(ln(divide(calculateFinalCellDensity(), input.cultivationSettings.initialCellDensity)), input.cultivationSettings.maxGrowthRate!!))
    }

    fun calculateFinalCellDensity(): Double {
        return (input.cultivationSettings.initialCellDensity + divide(
            multiply(input.cultivationSettings.initialSugarConcentration, input.cultivationSettings.yield!!),
            (1 + divide(multiply(input.cultivationSettings.maintenance!!, input.cultivationSettings.yield), input.cultivationSettings.maxGrowthRate!!))
        ))
    }

    fun calculateCellDensity(timePoint: Double): Double {
        return round(multiply(input.cultivationSettings.initialCellDensity, exp(multiply(input.cultivationSettings.maxGrowthRate!!, timePoint))))
    }

    fun calculateSugarConcentration(timePoint: Double): Double {
        return round(
            input.cultivationSettings.initialSugarConcentration - multiply(
                multiply(
                    calculateSugarUptakeRate(),
                    calculateCellDensity(timePoint)
                ), timePoint
            )
        )
    }

    fun calculateSugarUptakeRate(): Double {
        return (divide(input.cultivationSettings.maxGrowthRate!!, input.cultivationSettings.yield!!) + input.cultivationSettings.maintenance!!)
    }

    /**
     * Calculations to model the energy usage related to mixing and power consumption
     *
     * Assume low viscosity medium, assume turbulent flow
     */

    fun calculateMixingTime(): Double {
        return if (input.reactorSettings.nominalVolume!! < 60) {
            round(multiply(4.toDouble(), calculateCirculationTime()))
        } else {
            round(multiply(223.5, log(multiply(input.reactorSettings.workingVolume!!, 1000.toDouble()), 10.0)) - 1004.6)
        }
    }

    private fun calculateCirculationTime(): Double {
        return divide(input.reactorSettings.workingVolume!!, calculatePumpingCapacity())
    }

    private fun calculatePumpingCapacity(): Double {
        return (multiply(
            multiply(input.reactorSettings.impellerFlowNumber!!, input.reactorSettings.agitatorSpeed),
            input.reactorSettings.impellerDiameter!!.pow(3)
        ))
    }

    fun calculatePowerConsumptionKWh(): Double {
        return round(divide(multiply(calculatePowerConsumptionWatts(), calculateDuration()), 1000.0))
    }

    private fun calculatePowerConsumptionWatts(): Double {
        return (multiply(
            multiply(
                multiply(
                    multiply(
                        (input.reactorSettings.numberOfImpellers).toDouble(),
                        input.reactorSettings.impellerPowerNumber!!
                    ), input.reaction.liquidDensity
                ), (input.reactorSettings.agitatorSpeed).pow(3)
            ), (input.reactorSettings.impellerDiameter)!!.pow(5)
        ))
    }

    fun calculatePowerConsumptionWattsPerCube(): Double {
        val x = calculatePowerConsumptionWatts()
        return round(divide(x, input.reactorSettings.workingVolume!!))
    }

    /**
     * Calculations to model the heat production inside the reactor and thus required cooling
     *
     * Heating up before the process is not accounted for
     * Constants used assume an ethanol production process by S.cerevisiae from glucose
     */


}
