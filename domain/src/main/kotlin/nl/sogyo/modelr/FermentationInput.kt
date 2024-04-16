package nl.sogyo.modelr

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class FermentationInput(
    val initialSugarConcentration: Double, //g L-1
    val initialCellDensity: Double, //g L-1
    val maxGrowthRate: Double, //h-1
    val maintenance: Double, //g gx-1 h-1
    val yield: Double //-
)
