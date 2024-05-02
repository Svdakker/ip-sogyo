package nl.sogyo.modelr.data.centrifugationRequest

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class CentrifugationInput(
    val brothProperties: BrothProperties = BrothProperties(),
    val centrifugeProperties: CentrifugeProperties = CentrifugeProperties(),
    val centrifugationSettings: CentrifugationSettings = CentrifugationSettings(),
)