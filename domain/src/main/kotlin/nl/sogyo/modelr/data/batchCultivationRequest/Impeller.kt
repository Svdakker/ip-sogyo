package nl.sogyo.modelr.data.batchCultivationRequest

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class Impeller(
    val type: String,
    val impellerDiameter: Double,
    val impellerFlowNumber: Double,
    val impellerPowerNumber: Double,
)
