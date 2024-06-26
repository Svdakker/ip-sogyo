package nl.sogyo.modelr.data.batchCultivationRequest

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import nl.sogyo.modelr.data.CostFactors

@JsonIgnoreProperties(ignoreUnknown = true)
data class BatchCultivation(
    val position: Int,
    val request: BatchCultivationInput,
    val costFactor: CostFactors,
    val microorganism: Microorganism,
    val reactor: Reactor,
    val impeller: Impeller,
)