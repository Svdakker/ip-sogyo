package nl.sogyo.modelr.data

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class SimulationOutput(
    val output: List<OperationOutput?> = emptyList(),
)
