package nl.sogyo.modelr.data

data class OperationOutput(
    val duration: Number,
    val model: Map<Double, List<Double>>,
)
