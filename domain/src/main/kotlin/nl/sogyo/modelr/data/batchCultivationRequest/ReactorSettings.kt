package nl.sogyo.modelr.data.batchCultivationRequest

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class ReactorSettings(
        val nominalVolume: Double?, //m3
        val workingVolume: Double?, //m3
        val height: Double?, //m
        val width: Double?, //m (diameter)
        val impellerType: String,
        val numberOfImpellers: Int,
        val agitatorSpeed: Double, //s-1
        val impellerDiameter: Double? = null, //m
        val impellerFlowNumber: Double? = null,
        val impellerPowerNumber: Double? = null,
)
