package nl.sogyo.modelr.entities

import jakarta.persistence.*
import java.time.LocalDate

@Entity
class Reactor(
    @Column(nullable = false)
    var date: LocalDate,

    @Column(nullable = false, unique = true)
    var name: String,

    @Column(nullable = false)
    var nominalVolume: Double,

    @Column(nullable = false)
    var workingVolume: Double,

    @Column(nullable = false)
    var height: Double,

    @Column(nullable = false)
    var width: Double,

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
)