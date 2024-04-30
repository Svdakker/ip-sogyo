package nl.sogyo.modelr

import nl.sogyo.modelr.data.*
import nl.sogyo.modelr.data.batchCultivationRequest.BatchCultivationInput
import nl.sogyo.modelr.data.batchCultivationRequest.CostFactors
import kotlin.math.exp
import kotlin.math.ln
import kotlin.math.log
import kotlin.math.pow

class BatchCultivationOperation(private val input: BatchCultivationInput,
                                private val costs: CostFactors = CostFactors(),
                                private val nextOperation: UnitOperation? = null) :
    UnitOperation() {

    private var initialCellDensity = input.cultivationSettings.initialCellDensity

    override fun generateOutput(previousResult: OperationOutput?, previousOperation: UnitOperation?): OperationOutput {
        if (previousResult != null && previousOperation != null) {
            setInitialCellDensity(findInitialCellDensity(previousResult, previousOperation))
        }
        return OperationOutput(calculateDuration(), modelOperation(), calculateCosts(), calculateEnergyConsumption())
    }

    override fun getNextOperation(): UnitOperation? {
        return this.nextOperation
    }

    private fun getInitialCellDensity(): Double {
        return this.initialCellDensity
    }

    private fun setInitialCellDensity(initialCellDensity: Double) {
        this.initialCellDensity = initialCellDensity
    }

    private fun getCultivationInput(): BatchCultivationInput {
        return this.input
    }

    private fun findInitialCellDensity(previousResult: OperationOutput, previousOperation: UnitOperation): Double {
        return when (previousOperation::class) {
            BatchCultivationOperation::class -> calculateInitialCellDensity(previousResult, previousOperation as BatchCultivationOperation)
            else -> throw IllegalArgumentException("Operation not supported as previous operation (${previousOperation::class.simpleName})")
        }
    }

    private fun calculateInitialCellDensity(previousResult: OperationOutput, previousOperation: BatchCultivationOperation): Double {
        val previousModel = previousResult.model
        val finalCellDensity = previousModel[previousModel.size - 2][1]
        val finalCellMass = multiply(finalCellDensity, previousOperation.getCultivationInput().reactorSettings.workingVolume!!)
        return divide(finalCellMass, input.reactorSettings.workingVolume!!)
    }

    /**
     * Calculations to model a batch cultivation over time
     *
     * Calculations are now based on an ethanol production process from glucose, by S.cerevisiae. In future releases,
     * the user should be able to choose microorganisms, substrates and desired products
     * Process taken from: Design of a batch stirred fermenter for ethanol production, Mohammad Emal Qazizada
     */

    override fun modelOperation(): List<List<Double>> {
        val model = mutableListOf<List<Double>>()
        modelDataPoints(model,0.0)
        return model
    }

    private fun modelDataPoints(model: MutableList<List<Double>>, time: Double) {
        if (calculateSugarConcentration(time) >= 0) {
            model.add(calculateDataPoint(time))
            modelDataPoints(model,round(time + input.cultivationSettings.accuracy))
        } else {
            model.add(calculateDataPoint(time))
        }
    }

    private fun calculateDataPoint(timePoint: Double): List<Double> {
        return listOf(timePoint, calculateCellDensity(timePoint), calculateSugarConcentration(timePoint))
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
        return round(divide(ln(divide(calculateFinalCellDensity(), getInitialCellDensity())), input.cultivationSettings.maxGrowthRate!!))
    }

    fun calculateFinalCellDensity(): Double {
        return (getInitialCellDensity() + divide(
            multiply(input.cultivationSettings.initialSugarConcentration, input.cultivationSettings.yield!!),
            (1 + divide(multiply(input.cultivationSettings.maintenance!!, input.cultivationSettings.yield), input.cultivationSettings.maxGrowthRate!!))
        ))
    }

    fun calculateCellDensity(timePoint: Double): Double {
        return round(multiply(getInitialCellDensity(), exp(multiply(input.cultivationSettings.maxGrowthRate!!, timePoint))))
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

}
