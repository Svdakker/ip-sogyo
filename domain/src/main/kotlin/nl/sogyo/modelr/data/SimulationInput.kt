package nl.sogyo.modelr.data

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import nl.sogyo.modelr.data.batchCultivationRequest.BatchCultivation
import nl.sogyo.modelr.data.centrifugationRequest.Centrifugation

@JsonIgnoreProperties(ignoreUnknown = true)
data class SimulationInput(
    val batchCultivation: List<BatchCultivation?> = emptyList(),
    val centrifugation: List<Centrifugation?> = emptyList()
)
