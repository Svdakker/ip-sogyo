package nl.sogyo.modelr.data

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class Microorganism(
    val name: String,
    val maxGrowthRate: Double,
    val maintenance: Double,
    val yield: Double,
)
