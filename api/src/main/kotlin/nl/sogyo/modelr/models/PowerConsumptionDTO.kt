package nl.sogyo.modelr.models

import com.fasterxml.jackson.annotation.JsonProperty

data class PowerConsumptionDTO(
    @JsonProperty
    var operations: Double,
)