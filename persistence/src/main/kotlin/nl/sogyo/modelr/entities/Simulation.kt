package nl.sogyo.modelr.entities

import jakarta.persistence.*

@Entity
class Simulation(
    @OneToMany(cascade = [CascadeType.ALL])
    @JoinColumn(name = "simulation_id", nullable = true)
    var batchCultivation: List<BatchCultivation?> = emptyList(), //foreign key to possible BatchCultivations

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
)
