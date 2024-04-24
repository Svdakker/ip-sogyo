package nl.sogyo.modelr.entities

import jakarta.persistence.*
import java.time.LocalDate

@Entity
class Microorganism (
    @Column(nullable = false)
    var date: LocalDate,

    @Column(nullable = false, unique = true)
    var name: String,

    @Column(nullable = false)
    var maxGrowthRate: Double,

    @Column(nullable = false)
    var yield: Double,

    @Column(nullable = false)
    var maintenance: Double,

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,
)