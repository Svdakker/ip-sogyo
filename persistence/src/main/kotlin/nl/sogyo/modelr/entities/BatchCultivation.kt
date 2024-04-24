package nl.sogyo.modelr.entities

import jakarta.persistence.*

@Entity
class BatchCultivation(
    @Column(nullable = false)
    var position: Int, //Position in the cascade

    @Column(columnDefinition = "nvarchar(1000)", nullable = false)
    var request: String, //Contains SimulationRequestDTO as Json

    @Column(nullable = true)
    var result: String? = null, //Contains SimulationResultDTO as Json

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "cost_id", nullable = false)
    var costFactor: CostFactor, //Foreign key to CostData

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "microorganism_id", nullable = false)
    var microorganism: Microorganism, //Foreign key to CultivationData

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "reactor_id", nullable = false)
    var reactor: Reactor, //Foreign key to EquipmentData

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "impeller_id", nullable = false)
    var impeller: Impeller, //Foreign key to impellerData

    @Id @GeneratedValue
    var id: Long?,
)
