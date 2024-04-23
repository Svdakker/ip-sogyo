package nl.sogyo.modelr.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id

@Entity
class Impeller(
    @Column(nullable = false, unique = true)
    var type: String,

    @Column(nullable = false)
    var impellerDiameter: Double,

    @Column(nullable = false)
    var impellerFlowNumber: Double,

    @Column(nullable = false)
    var impellerPowerNumber: Double,

    @Id @GeneratedValue
    var id: Long?,
)
