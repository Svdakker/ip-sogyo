package nl.sogyo.modelr

import nl.sogyo.modelr.data.OperationOutput
import java.math.RoundingMode

abstract class UnitOperation {

    val divide = { x: Double, y: Double -> x / y}

    val multiply = { x: Double, y: Double -> x * y }

    val round = { x: Double -> x.toBigDecimal().setScale(2, RoundingMode.HALF_UP).toDouble() }

    abstract fun generateOutput(): OperationOutput
}