package nl.sogyo.modelr.entities

import jakarta.persistence.*

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

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
)
