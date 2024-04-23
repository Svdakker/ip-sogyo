package nl.sogyo.modelr.entities

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import java.time.LocalDate

@Entity
class Reactor(
    @Id @GeneratedValue
    var id: Long?,
    var date: LocalDate,
    var name: String,
    var nominalVolume: Double,
    var workingVolume: Double,
    var height: Double,
    var width: Double,
)