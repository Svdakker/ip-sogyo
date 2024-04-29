package nl.sogyo.modelr.models

import com.fasterxml.jackson.annotation.JsonProperty

data class SimulationResultDTO(
    @JsonProperty
    var batchCultivation: OperationResultDTO? = null
)
