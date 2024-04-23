package nl.sogyo.modelr.models

import com.fasterxml.jackson.annotation.JsonProperty

data class CultivationSettingsDTO(
    @JsonProperty
    var microorganism: String,
    @JsonProperty
    var accuracy: Double,
    @JsonProperty
    var initialSugarConcentration: Double, //g L-1
    @JsonProperty
    var initialCellDensity: Double, //g L-1
    @JsonProperty
    var maxGrowthRate: Double, //h-1
    @JsonProperty
    var maintenance: Double, //g gx-1 h-1
    @JsonProperty
    var yield: Double //-
)
