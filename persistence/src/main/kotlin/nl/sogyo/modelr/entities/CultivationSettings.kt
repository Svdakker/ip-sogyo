package nl.sogyo.modelr.entities

import jakarta.persistence.*

@Entity
class CultivationSettings(
    @Column(nullable = false)
    var microorganism: String,

    @Column(nullable = false)
    var accuracy: Double,

    @Column(nullable = false)
    var initialSugarConcentration: Double,

    @Column(nullable = false)
    var initialCellDensity: Double,

    @Column(nullable = true)
    var maxGrowthRate: Double? = null,

    @Column(nullable = true)
    var maintenance: Double? = null,

    @Column(nullable = true)
    var yield: Double? = null,

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
)