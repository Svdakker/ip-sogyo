package nl.sogyo.modelr.entities

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import java.time.LocalDate

@Entity
data class CostFactor(
    @Id @GeneratedValue
    var id: Long?,
    var date: LocalDate,
    var energy: Double,
)