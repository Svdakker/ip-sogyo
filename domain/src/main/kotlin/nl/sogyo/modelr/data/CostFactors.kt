package nl.sogyo.modelr.data

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class CostFactors(
    val energy: Double = 0.15, //euro/kwH
)
