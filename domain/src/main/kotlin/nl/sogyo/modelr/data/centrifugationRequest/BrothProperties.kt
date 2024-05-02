package nl.sogyo.modelr.data.centrifugationRequest

data class BrothProperties(
    val particleDiameter: Double = 3.8E-6, //m
    val densitySolids: Double = 1130.0, //kg m-3
    val densityLiquid: Double = 1005.0, //kg m-3
    val liquidViscosity: Double = 1.1E-3, //kg m-1 s-1
)
