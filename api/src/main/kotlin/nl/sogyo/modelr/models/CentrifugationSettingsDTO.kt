package nl.sogyo.modelr.models

import com.fasterxml.jackson.annotation.JsonProperty

data class CentrifugationSettingsDTO(
    @JsonProperty
    var frequencyOfRotation: Double,
    @JsonProperty
    var liquidFlowRate: Double,
    @JsonProperty
    var liquidVolume: Double? = null
)
