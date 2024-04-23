package nl.sogyo.modelr.entities

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id

@Entity
class Impeller(
    @Id @GeneratedValue
    var id: Long?,
    var type: String,
    var impellerDiameter: Double,
    var impellerFlowNumber: Double,
    var impellerPowerNumber: Double,
)
