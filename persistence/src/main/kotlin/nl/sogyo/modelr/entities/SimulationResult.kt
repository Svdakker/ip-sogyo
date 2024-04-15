package nl.sogyo.modelr.entities

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id

@Entity
data class SimulationResult(
    @Id @GeneratedValue
    var id: Long?,
    var duration: Number
)