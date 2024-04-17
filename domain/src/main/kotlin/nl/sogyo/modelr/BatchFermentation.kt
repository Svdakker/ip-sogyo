package nl.sogyo.modelr

import nl.sogyo.modelr.data.FermentationInput
import nl.sogyo.modelr.data.OperationOutput
import kotlin.math.exp
import kotlin.math.ln

class BatchFermentation(private val input: FermentationInput) : UnitOperation() {

    override fun generateOutput(): OperationOutput {
        return OperationOutput(calculateDuration(), modelOperation())
    }

    fun modelOperation(): Map<Double, List<Double>> {
        val model = mutableMapOf<Double, List<Double>>()
        modelDataPoints(model,0.0)
        return model
    }

    private fun modelDataPoints(model: MutableMap<Double, List<Double>>, datapoint: Double) {
        if (calculateSugarConcentration(datapoint) >= 0) {
            model[datapoint] = calculateDataPoint(datapoint)
            modelDataPoints(model,datapoint + input.accuracy)
        } else {
            model[datapoint] = calculateDataPoint(datapoint)
        }
    }

    fun calculateDataPoint(timePoint: Double): List<Double> {
        return listOf(calculateCellDensity(timePoint), calculateSugarConcentration(timePoint))
    }

    fun calculateDuration(): Double {
        return round(
            multiply(
                divide(1.toDouble(), input.maxGrowthRate),
                ln(
                    multiply(
                        divide(input.maxGrowthRate, (divide(input.maxGrowthRate, input.yield) + input.maintenance)),
                        divide(input.initialSugarConcentration, input.initialCellDensity)
                    ) + 1
                )
            )
        )
    }

    fun calculateCellDensity(timePoint: Double): Double {
        return round(multiply(input.initialCellDensity, exp(multiply(input.maxGrowthRate, timePoint))))
    }

    fun calculateSugarUptakeRate(): Double {
        return round(divide(input.maxGrowthRate, input.yield) + input.maintenance)
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
}
