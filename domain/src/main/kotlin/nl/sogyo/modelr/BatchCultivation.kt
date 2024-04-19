package nl.sogyo.modelr

import nl.sogyo.modelr.data.*
import kotlin.math.exp
import kotlin.math.ln
import kotlin.math.log
import kotlin.math.pow

class BatchCultivation(private val input: CultivationInput, private val costs: CostFactors = CostFactors()) : UnitOperation() {

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
            modelDataPoints(model,time + input.accuracy)
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
        return round(divide(ln(divide(calculateFinalCellDensity(), input.initialCellDensity)), input.maxGrowthRate))
    }

    fun calculateFinalCellDensity(): Double {
        return (input.initialCellDensity + divide(
            multiply(input.initialSugarConcentration, input.yield),
            (1 + divide(multiply(input.maintenance, input.yield), input.maxGrowthRate))
        ))
    }

    fun calculateCellDensity(timePoint: Double): Double {
        return round(multiply(input.initialCellDensity, exp(multiply(input.maxGrowthRate, timePoint))))
    }

    fun calculateSugarConcentration(timePoint: Double): Double {
        return round(
            input.initialSugarConcentration - multiply(
                multiply(
                    calculateSugarUptakeRate(),
                    calculateCellDensity(timePoint)
                ), timePoint
            )
        )
    }

    fun calculateSugarUptakeRate(): Double {
        return (divide(input.maxGrowthRate, input.yield) + input.maintenance)
    }

    /**
     * Calculations to model the energy usage related to mixing and power consumption
     *
     * Assume low viscosity medium, assume turbulent flow
     */

    fun calculateMixingTime(): Double {
        return if (input.reactor.nominalVolume < 60) {
            round(multiply(4.toDouble(), calculateCirculationTime()))
        } else {
            round(multiply(223.5, log(multiply(input.reactor.workingVolume, 1000.toDouble()), 10.0)) - 1004.6)
        }
    }

    private fun calculateCirculationTime(): Double {
        return divide(input.reactor.workingVolume, calculatePumpingCapacity())
    }

    private fun calculatePumpingCapacity(): Double {
        return (multiply(
            multiply(input.reactor.impellerFlowNumber, input.agitatorSpeed),
            input.reactor.impellerDiameter.pow(3)
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
                        (input.reactor.numberOfImpellers).toDouble(),
                        input.reactor.impellerPowerNumber
                    ), input.reaction.liquidDensity
                ), (input.agitatorSpeed).pow(3)
            ), (input.reactor.impellerDiameter).pow(5)
        ))
    }

    fun calculatePowerConsumptionWattsPerCube(): Double {
        val x = calculatePowerConsumptionWatts()
        return round(divide(x, input.reactor.workingVolume))
    }

    /**
     * Calculations to model the heat production inside the reactor and thus required cooling
     *
     * Heating up before the process is not accounted for
     * Constants used assume an ethanol production process by S.cerevisiae from glucose
     */


}
