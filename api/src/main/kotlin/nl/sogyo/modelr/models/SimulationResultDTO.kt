package nl.sogyo.modelr.models

import com.fasterxml.jackson.annotation.JsonProperty
import nl.sogyo.modelr.data.DataPoint

data class SimulationResultDTO(
    @JsonProperty("id")
    var id: Long,
    @JsonProperty("duration")
    var duration: Number,
    @JsonProperty("model")
    var model: List<DataPoint>
)
