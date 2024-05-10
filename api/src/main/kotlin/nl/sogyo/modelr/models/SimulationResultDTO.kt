package nl.sogyo.modelr.models

import com.fasterxml.jackson.annotation.JsonProperty

data class SimulationResultDTO(
    @JsonProperty
    var order: List<String?> = emptyList(),
    @JsonProperty
    var output: List<OperationResultDTO?> = emptyList()
)
