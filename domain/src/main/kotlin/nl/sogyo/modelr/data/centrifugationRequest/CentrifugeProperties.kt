package nl.sogyo.modelr.data.centrifugationRequest

data class CentrifugeProperties(
    val outerRadius: Double = 8.0E-2, //m
    val innerRadius: Double = 2.2E-3, //m
    val numberOfDisks: Int = 50, //-
    val diskAngle: Double = 45.0, //Deg
    val motorPower: Double = 5.0 //kW
)
