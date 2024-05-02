package nl.sogyo.modelr.data

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import nl.sogyo.modelr.data.batchCultivationRequest.BatchCultivation

@JsonIgnoreProperties(ignoreUnknown = true)
data class SimulationInput(
    val batchCultivation: List<BatchCultivation?> = emptyList()
)
