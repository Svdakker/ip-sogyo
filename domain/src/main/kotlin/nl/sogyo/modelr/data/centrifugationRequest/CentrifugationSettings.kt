package nl.sogyo.modelr.data.centrifugationRequest

data class CentrifugationSettings(
    val frequencyOfRotation: Double = 9200.0, //rpm
    val liquidFlowRate: Double = 3.22E-5, //m3 s-1
    val liquidVolume: Double = 52.5E-3 //m3
)
