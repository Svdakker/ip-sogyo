package nl.sogyo.modelr.models

import com.fasterxml.jackson.annotation.JsonProperty
import nl.sogyo.modelr.data.CostEstimation
import nl.sogyo.modelr.data.DataPoint
import nl.sogyo.modelr.data.PowerConsumption

data class OperationResultDTO(
    @JsonProperty("id")
    var id: Long,
    @JsonProperty("duration")
    var duration: Number,
    @JsonProperty("model")
    var model: List<DataPoint>,
    @JsonProperty("costEstimation")
    var costEstimation: CostEstimation,
    @JsonProperty("powerConsumption")
    var powerConsumption: PowerConsumption,
)
