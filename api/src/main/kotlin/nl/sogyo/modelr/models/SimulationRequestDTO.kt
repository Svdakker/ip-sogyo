package nl.sogyo.modelr.models

import com.fasterxml.jackson.annotation.JsonProperty

data class SimulationRequestDTO (
    @JsonProperty
    var operationType: String,
    @JsonProperty
    var simulationAccuracy: Double,
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