package nl.sogyo.modelr.data

data class OperationOutput(
    val duration: Number,
    val model: List<DataPoint>,
    val costEstimation: CostEstimation,
    val powerConsumption: PowerConsumption,
)
