package nl.sogyo.modelr.entities

import jakarta.persistence.*
import java.time.LocalDate

@Entity
class CostFactor(
    @Column(nullable = false)
    var date: LocalDate,

    @Column(nullable = false)
    var energy: Double,

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
)