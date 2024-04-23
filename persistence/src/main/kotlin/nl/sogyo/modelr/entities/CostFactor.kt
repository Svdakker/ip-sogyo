package nl.sogyo.modelr.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import java.time.LocalDate

@Entity
class CostFactor(
    @Column(nullable = false)
    var date: LocalDate,

    @Column(nullable = false)
    var energy: Double,

    @Id @GeneratedValue
    var id: Long?,
)