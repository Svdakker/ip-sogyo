package nl.sogyo.modelr.data.centrifugationRequest

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class CentrifugationSettings(
    val frequencyOfRotation: Double, //rpm
    val liquidFlowRate: Double, //m3 s-1
    val liquidVolume: Double?//m3
)
