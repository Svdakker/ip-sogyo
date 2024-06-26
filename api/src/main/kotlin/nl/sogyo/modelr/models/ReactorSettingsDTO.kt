package nl.sogyo.modelr.models

import com.fasterxml.jackson.annotation.JsonProperty

data class ReactorSettingsDTO(
    @JsonProperty
    var reactorType: String,
    @JsonProperty
    var nominalVolume: Double? = null,
    @JsonProperty
    var workingVolume: Double? = null, //g L-1
    @JsonProperty
    var height: Double? = null, //g L-1
    @JsonProperty
    var width: Double? = null, //h-1
    @JsonProperty
    var impellerType: String, //g gx-1 h-1
    @JsonProperty
    var numberOfImpellers: Int, //-
    @JsonProperty
    var agitatorSpeed: Double,
)