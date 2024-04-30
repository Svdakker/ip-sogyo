package nl.sogyo.modelr.models

import com.fasterxml.jackson.annotation.JsonProperty

data class SimulationResultDTO(
    @JsonProperty
    var output: List<OperationResultDTO?> = emptyList()
)
