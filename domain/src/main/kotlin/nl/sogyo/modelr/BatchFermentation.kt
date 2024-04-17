package nl.sogyo.modelr

import nl.sogyo.modelr.data.DataPoint
import nl.sogyo.modelr.data.FermentationInput
import nl.sogyo.modelr.data.OperationOutput
import kotlin.math.exp
import kotlin.math.ln

class BatchFermentation(private val input: FermentationInput) : UnitOperation() {

    override fun generateOutput(): OperationOutput {
        val x = calculateDuration()
        val y = modelOperation()
        return OperationOutput(x, y)
    }

    fun modelOperation(): List<DataPoint> {
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

    fun calculateDataPoint(timePoint: Double): DataPoint {
        return DataPoint(timePoint, calculateCellDensity(timePoint), calculateSugarConcentration(timePoint))
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
