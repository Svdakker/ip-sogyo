package nl.sogyo.modelr.models

import com.fasterxml.jackson.annotation.JsonProperty

data class CentrifugePropertiesDTO(
    @JsonProperty
    var centrifugeType: String,
    @JsonProperty
    var outerRadius: Double? = null, //m
    @JsonProperty
    var innerRadius: Double? = null, //m
    @JsonProperty
    var numberOfDisks: Int? = null, //-
    @JsonProperty
    var diskAngle: Double? = null, //Deg
    @JsonProperty
    var motorPower: Double? = null, //kW
)
