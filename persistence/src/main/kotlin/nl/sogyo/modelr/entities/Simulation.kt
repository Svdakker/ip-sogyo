package nl.sogyo.modelr.entities

import jakarta.persistence.*

@Entity
class Simulation(
    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "batch_id", nullable = true)
    var batchCultivation: BatchCultivation?, //foreign key to possible BatchCultivation

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,
)
