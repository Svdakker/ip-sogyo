package nl.sogyo.modelr.data

data class ReactorSettings(
        val nominalVolume: Double = 70.0, //m3
        val workingVolume: Double = 52.5, //m3
        val height: Double = 9.29, //m
        val width: Double = 3.10, //m (diameter)
        val impellerType: String = "rushton turbine",
        val numberOfImpellers: Int = 4,
        val agitatorSpeed: Double = 2.5, //s-1
        val impellerDiameter: Double = 0.97, //m
        val impellerFlowNumber: Double = 0.72,
        val impellerPowerNumber: Double = 5.2,
)
