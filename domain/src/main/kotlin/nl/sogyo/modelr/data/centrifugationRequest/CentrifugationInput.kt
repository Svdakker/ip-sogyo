package nl.sogyo.modelr.data.centrifugationRequest

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class CentrifugationInput(
    val brothProperties: BrothProperties,
    val centrifugeProperties: CentrifugeProperties,
    val centrifugationSettings: CentrifugationSettings,
)