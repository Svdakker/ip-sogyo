package nl.sogyo.modelr.models

import com.fasterxml.jackson.annotation.JsonProperty

data class ReactorSettingsDTO(
    @JsonProperty
    var nominalVolume: Double,
    @JsonProperty
    var workingVolume: Double, //g L-1
    @JsonProperty
    var height: Double, //g L-1
    @JsonProperty
    var width: Double, //h-1
    @JsonProperty
    var impellerType: String, //g gx-1 h-1
    @JsonProperty
    var numberOfImpellers: Double, //-
    @JsonProperty
    var agitatorSpeed: Double,
)