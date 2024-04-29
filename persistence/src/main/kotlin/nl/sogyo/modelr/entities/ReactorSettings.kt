package nl.sogyo.modelr.entities

import jakarta.persistence.*

@Entity
class ReactorSettings(
    @Column(nullable = false)
    var reactorType: String,

    @Column(nullable = true)
    var nominalVolume: Double? = null,

    @Column(nullable = true)
    var workingVolume: Double? = null,

    @Column(nullable = true)
    var height: Double? = null,

    @Column(nullable = true)
    var width: Double? = null,

    @Column(nullable = false)
    var impellerType: String,

    @Column(nullable = false)
    var numberOfImpellers: Int,

    @Column(nullable = false)
    var agitatorSpeed: Double,

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
)