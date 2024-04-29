package nl.sogyo.modelr.data.centrifugationRequest

data class CentrifugationSettings(
    val frequencyOfRotation: Double = 9200.0, //rpm
    val liquidFlowRate: Double = 1.74E-5 //m3 s-1
)
