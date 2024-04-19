package nl.sogyo.modelr.data

data class BioreactorProperties(
    val nominalVolume: Double = 70.0, //m3
    val workingVolume: Double = 52.5, //m3
    val vesselHeight: Double = 9.29, //m
    val vesselWidth: Double = 3.10, //m (diameter)
    val impellerType: String = "Rushton turbine",
    val impellerDiameter: Double = 0.97, //m
    val impellerFlowNumber: Double = 0.72,
    val impellerPowerNumber: Double = 5.2,
    val numberOfImpellers: Int = 4,
)
