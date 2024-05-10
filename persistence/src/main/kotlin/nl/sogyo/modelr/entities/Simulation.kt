package nl.sogyo.modelr.entities

import jakarta.persistence.*

@Entity
class Simulation(
    @OneToMany(cascade = [CascadeType.ALL])
    @JoinColumn(name = "simulation_id", nullable = true)
    var batchCultivation: List<BatchCultivation?> = emptyList(), //foreign key to possible BatchCultivations

    @OneToMany(cascade = [CascadeType.ALL])
    @JoinColumn(name = "centrifugation_id", nullable = true)
    var centrifugation: List<Centrifugation?> = emptyList(), //foreign key to possible Centrifugations

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
)
