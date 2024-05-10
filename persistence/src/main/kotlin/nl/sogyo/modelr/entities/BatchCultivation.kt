package nl.sogyo.modelr.entities

import jakarta.persistence.*

@Entity
class BatchCultivation(
    @Column(nullable = false)
    var position: Int, //Position in the cascade

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "request_id", nullable = false)
    var request: BatchRequest, //Contains SimulationRequestDTO as Json

    @Column(columnDefinition = "TEXT", nullable = true)
    var result: String? = null, //Contains SimulationResultDTO as Json

    @ManyToOne(optional = false, cascade = [CascadeType.ALL])
    @JoinColumn(name = "cost_id", nullable = false)
    var costFactor: CostFactor, //Foreign key to CostData

    @ManyToOne(optional = false, cascade = [CascadeType.ALL])
    @JoinColumn(name = "microorganism_id", nullable = false)
    var microorganism: Microorganism, //Foreign key to CultivationData

    @ManyToOne(optional = false, cascade = [CascadeType.ALL])
    @JoinColumn(name = "reactor_id", nullable = false)
    var reactor: Reactor, //Foreign key to EquipmentData

    @ManyToOne(optional = false, cascade = [CascadeType.ALL])
    @JoinColumn(name = "impeller_id", nullable = false)
    var impeller: Impeller, //Foreign key to impellerData

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
)
