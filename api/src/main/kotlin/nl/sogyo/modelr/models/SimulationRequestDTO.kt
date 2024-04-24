package nl.sogyo.modelr.models

import com.fasterxml.jackson.annotation.JsonProperty

data class SimulationRequestDTO(
    @JsonProperty
    var batchCultivation: BatchCultivationRequestDTO?,
)
