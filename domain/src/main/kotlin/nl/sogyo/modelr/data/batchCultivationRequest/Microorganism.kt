package nl.sogyo.modelr.data.batchCultivationRequest

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class Microorganism(
    val name: String,
    val maxGrowthRate: Double,
    val maintenance: Double,
    val yield: Double,
)
