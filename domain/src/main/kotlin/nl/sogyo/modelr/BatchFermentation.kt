package nl.sogyo.modelr

import kotlin.math.ln

class BatchFermentation(private val input: FermentationInput) : UnitOperation() {

    override fun generateOutput(): OperationOutput {
        return OperationOutput(calculateDuration())
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
}
