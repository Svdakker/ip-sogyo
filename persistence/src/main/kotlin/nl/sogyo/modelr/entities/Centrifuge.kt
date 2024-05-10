package nl.sogyo.modelr.entities

import jakarta.persistence.*
import java.time.LocalDate

@Entity
class Centrifuge(
    @Column(nullable = false)
    var date: LocalDate,

    @Column(nullable = false, unique = true)
    var name: String,

    @Column(nullable = false)
    var outerRadius: Double, //m

    @Column(nullable = false)
    var innerRadius: Double, //m

    @Column(nullable = false)
    var numberOfDisks: Int, //-

    @Column(nullable = false)
    var diskAngle: Double, //Deg

    @Column(nullable = false)
    var motorPower: Double,

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
)