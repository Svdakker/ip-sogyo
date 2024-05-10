package nl.sogyo.modelr.data.centrifugationRequest

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import nl.sogyo.modelr.data.CostFactors

@JsonIgnoreProperties(ignoreUnknown = true)
data class Centrifugation(
    val position: Int,
    val request: CentrifugationInput,
    val costFactor: CostFactors,
    val centrifuge: CentrifugeProperties
)
