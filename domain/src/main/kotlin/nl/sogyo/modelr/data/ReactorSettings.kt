package nl.sogyo.modelr.data

data class ReactorSettings(
        val nominalVolume: Double, //m3
        val workingVolume: Double, //m3
        val height: Double, //m
        val width: Double, //m (diameter)
        val impellerType: String,
        val numberOfImpellers: Int,
        val agitatorSpeed: Double, //s-1
        val impellerDiameter: Double = 0.97, //m
        val impellerFlowNumber: Double = 0.72,
        val impellerPowerNumber: Double = 5.2,
)
