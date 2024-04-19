package nl.sogyo.modelr.data

data class ReactionProperties(
    val reactionHeat: Double = 45.1, //KJ mol-1
    val saturationConstant: Double = 2.0, //kg m3-1
    val inhibitionParameter: Double = 97.9, //kg m3-1
    val liquidDensity: Double = 1000.0, //kg m3-1
)
