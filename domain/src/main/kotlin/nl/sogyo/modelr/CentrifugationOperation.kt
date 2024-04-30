package nl.sogyo.modelr

import nl.sogyo.modelr.data.CostEstimation
import nl.sogyo.modelr.data.PowerConsumption
import nl.sogyo.modelr.data.batchCultivationRequest.CostFactors
import nl.sogyo.modelr.data.centrifugationRequest.CentrifugationInput
import kotlin.math.PI
import kotlin.math.pow
import kotlin.math.tan

class CentrifugationOperation(private val input: CentrifugationInput,
                              private val costs: CostFactors = CostFactors(),
                              private val nextOperation: UnitOperation? = null
) : UnitOperation() {

    override fun getNextOperation(): UnitOperation? {
        return this.nextOperation
    }

    /**
     * Calculations to model a centrifugation operation. Separation efficiency is modelled against chosen flow rate
     *
     * Calculations are based on an input slurry of a batch cultivation of yeast, producing ethanol. The solids are assumed to contain
     * the yeast cells, while the ethanol is assumed to reside in the liquid phase.
     *
     * DOI:10.1201
     * DOI:10.1080
     */

    override fun modelOperation(): List<List<Double>> {
        val model = mutableListOf<List<Double>>()
        modelDataPoints(model, input.centrifugationSettings.minLiquidFlowRate)
        return model
    }

    private fun modelDataPoints(model: MutableList<List<Double>>, flowRate: Double) {
        if (flowRate < input.centrifugationSettings.maxLiquidFlowRate) {
            model.add(calculateDataPoint(flowRate))
            modelDataPoints(model,flowRate + multiply(0.20, flowRate))
        } else {
            model.add(calculateDataPoint(input.centrifugationSettings.maxLiquidFlowRate))
        }
    }

    private fun calculateDataPoint(flowRate: Double): List<Double> {
        return listOf(flowRate, calculateEfficiencyOfSeparation(flowRate))
    }

    fun calculateEfficiencyOfSeparation(flowRate: Double): Double {
        return round(divide(multiply(calculateSettlingVelocity(), calculateSigmaFactor()), flowRate))
    }

    private fun calculateSettlingVelocity(): Double {
        val brothProperties = input.brothProperties
        return divide(
                multiply(
                    multiply(brothProperties.particleDiameter.pow(2), (brothProperties.densitySolids - brothProperties.densityLiquid)),
                    calculateRelativeGravitationalVelocity()
                ),
                multiply(brothProperties.liquidViscosity, 18.0)
            )
    }

    private fun calculateRelativeGravitationalVelocity(): Double {
        val centrifugeProperties = input.centrifugeProperties
        return multiply(calculateAngularVelocity().pow(2), (centrifugeProperties.outerRadius - centrifugeProperties.innerRadius))
    }

    private fun calculateSigmaFactor(): Double {
        val centrifugeProperties = input.centrifugeProperties
        return divide(
            multiply(
                multiply(
                    multiply(
                        multiply(2.0, PI),
                        centrifugeProperties.numberOfDisks.toDouble()
                    ),
                        calculateAngularVelocity().pow(2)
                ),
                (centrifugeProperties.outerRadius.pow(3) - centrifugeProperties.innerRadius.pow(2))
            ),
            multiply(
                multiply(3.0, 9.81),
                tan(centrifugeProperties.diskAngle))
        )
    }

    private fun calculateAngularVelocity(): Double {
        val centrifugationSettings = input.centrifugationSettings
        return multiply(multiply(2.0, PI), divide(centrifugationSettings.frequencyOfRotation, 60.0))
    }

    /**
     * Calculations to find the duration of a centrifugation operation
     *
     */

    override fun calculateDuration(): Double {
        return 100.0
    }

    /**
     * Calculations to find the energy consumption in a centrifugation operation
     *
     */

    override fun calculateEnergyConsumption(): PowerConsumption {
        return PowerConsumption(100.0)
    }

    /**
     * Calculations to find the costs related to running a centrifugation operation
     *
     */

    override fun calculateCosts(): CostEstimation {
        return CostEstimation(100.0)
    }
}