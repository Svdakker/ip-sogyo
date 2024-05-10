package nl.sogyo.modelr.entities

import jakarta.persistence.*

@Entity
class CentrifugationSettings(

    @Column(nullable = false)
    var frequencyOfRotation: Double, //rpm

    @Column(nullable = false)
    var liquidFlowRate: Double, //m3 s-1

    @Column(nullable = true)
    var liquidVolume: Double?,//m3

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
)