package nl.sogyo.modelr

import nl.sogyo.modelr.data.CostEstimation
import nl.sogyo.modelr.data.OperationOutput
import nl.sogyo.modelr.data.PowerConsumption
import java.math.RoundingMode

abstract class UnitOperation {

    abstract fun generateOutput(previousResult: OperationOutput? = null, previousOperation: UnitOperation? = null): OperationOutput

    abstract fun getNextOperation(): UnitOperation?

    abstract fun modelOperation(): List<List<Double>>

    abstract fun calculateDuration(): Double

    abstract fun calculateEnergyConsumption(): PowerConsumption

    abstract fun calculateCosts(): CostEstimation

    val divide = { x: Double, y: Double -> x / y}

    val multiply = { x: Double, y: Double -> x * y }

    val round = { x: Double -> x.toBigDecimal().setScale(2, RoundingMode.HALF_UP).toDouble() }
}