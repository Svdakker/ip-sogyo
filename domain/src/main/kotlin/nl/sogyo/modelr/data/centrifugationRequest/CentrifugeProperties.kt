package nl.sogyo.modelr.data.centrifugationRequest

data class CentrifugeProperties(
    val outerRadius: Double?, //m
    val innerRadius: Double?, //m
    val numberOfDisks: Int?, //-
    val diskAngle: Double?, //Deg
    val motorPower: Double? //kW
)
