package nl.sogyo.modelr.models

import com.fasterxml.jackson.annotation.JsonProperty

data class SimulationResultDTO(
    @JsonProperty("id")
    var id: Long,
    @JsonProperty("duration")
    var duration: Number,
)
