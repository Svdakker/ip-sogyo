package nl.sogyo.modelr

import nl.sogyo.modelr.data.CostEstimation
import nl.sogyo.modelr.data.PowerConsumption
import nl.sogyo.modelr.data.batchCultivationRequest.CostFactors
import nl.sogyo.modelr.data.centrifugationRequest.CentrifugationInput
import kotlin.math.PI
import kotlin.math.pow
import kotlin.math.tan

class CentrifugationOperation(private val input: CentrifugationInput, private val costs: CostFactors = CostFactors()) : UnitOperation() {

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
        TODO("Not yet implemented")
    }

    fun calculateEfficiencyOfSeparation(): Double {
        return round(divide(multiply(calculateSettlingVelocity(), calculateSigmaFactor()), input.centrifugationSettings.liquidFlowRate))
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
        return divide(multiply(calculateAngularVelocity().pow(2), (centrifugeProperties.outerRadius - centrifugeProperties.innerRadius)), 9.81)
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
        return divide(multiply(multiply(input.centrifugationSettings.frequencyOfRotation, PI), 2.0), 60.0)
    }

    override fun calculateDuration(): Double {
        TODO("Not yet implemented")
    }

    override fun calculateEnergyConsumption(): PowerConsumption {
        TODO("Not yet implemented")
    }

    override fun calculateCosts(): CostEstimation {
        TODO("Not yet implemented")
    }
}