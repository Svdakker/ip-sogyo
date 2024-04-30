package nl.sogyo.modelr.data.centrifugationRequest

data class CentrifugationSettings(
    val frequencyOfRotation: Double = 9200.0, //rpm
    val minLiquidFlowRate: Double = 3.22E-5, //m3 s-1
    val maxLiquidFlowRate: Double = 6.22E-5 //m3 s-1
)
