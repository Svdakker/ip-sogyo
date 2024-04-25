package nl.sogyo.modelr.data.batchCultivationRequest

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class Reactor(
    val name: String,
    val nominalVolume: Double,
    val workingVolume: Double,
    val height: Double,
    val width: Double,
)
