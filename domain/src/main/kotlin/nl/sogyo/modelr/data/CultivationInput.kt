package nl.sogyo.modelr.data

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class CultivationInput(
    val accuracy: Double,
    val initialSugarConcentration: Double, //kg m-3
    val initialCellDensity: Double, //kg m-3
    val maxGrowthRate: Double, //h-1
    val maintenance: Double, //kg kgx-1 h-1
    val yield: Double, //-
    val agitatorSpeed: Double = 2.5, //s-1
    val reactor: BioreactorProperties = BioreactorProperties(),
    val reaction: ReactionProperties = ReactionProperties()
)
