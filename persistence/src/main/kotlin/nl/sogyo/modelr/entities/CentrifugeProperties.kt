package nl.sogyo.modelr.entities

import jakarta.persistence.*

@Entity
class CentrifugeProperties(

    @Column(nullable = false)
    var centrifugeType: String,

    @Column(nullable = true)
    var outerRadius: Double?, //m

    @Column(nullable = true)
    var innerRadius: Double?, //m

    @Column(nullable = true)
    var numberOfDisks: Int?, //-

    @Column(nullable = true)
    var diskAngle: Double?, //Deg

    @Column(nullable = true)
    var motorPower: Double?, //kW

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
)
