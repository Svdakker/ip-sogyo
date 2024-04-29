package nl.sogyo.modelr.models

import com.fasterxml.jackson.annotation.JsonProperty

data class CostEstimationDTO(
    @JsonProperty
    val energy: Double
)
