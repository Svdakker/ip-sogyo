package nl.sogyo.modelr.models

import com.fasterxml.jackson.annotation.JsonProperty

data class OperationResultDTO(
    @JsonProperty("duration")
    var duration: Number,
    @JsonProperty("model")
    var model: List<List<Double>>,
    @JsonProperty("costEstimation")
    var costEstimation: CostEstimationDTO,
    @JsonProperty("powerConsumption")
    var powerConsumption: PowerConsumptionDTO,
)
