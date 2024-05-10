package nl.sogyo.modelr

import nl.sogyo.modelr.data.CostEstimation
import nl.sogyo.modelr.data.OperationOutput
import nl.sogyo.modelr.data.PowerConsumption
import java.math.RoundingMode

abstract class UnitOperation {

    fun generateOutput(previousResult: OperationOutput? = null, previousOperation: UnitOperation? = null): OperationOutput {
        if (previousOperation != null) {
            correctForPreviousOperation(previousResult!!, previousOperation)
        }
        return OperationOutput(calculateDuration(), modelOperation(), calculateCosts(), calculateEnergyConsumption())
    }

    private fun correctForPreviousOperation(previousResult: OperationOutput, previousOperation: UnitOperation) {
        if (previousOperation::class == BatchCultivationOperation::class) {
            setCorrection(previousResult, previousOperation as BatchCultivationOperation)
        } else {
            throw IllegalArgumentException("operation not supported as previous operation")
        }
    }

    abstract fun setCorrection(previousResult: OperationOutput, previousOperation: BatchCultivationOperation)

    abstract fun getNextOperation(): UnitOperation?

    abstract fun modelOperation(): List<List<Double>>

    abstract fun calculateDuration(): Double

    abstract fun calculateEnergyConsumption(): PowerConsumption

    abstract fun calculateCosts(): CostEstimation

    val divide = { x: Double, y: Double -> x / y}

    val multiply = { x: Double, y: Double -> x * y }

    val round = { x: Double -> x.toBigDecimal().setScale(2, RoundingMode.HALF_UP).toDouble() }
}