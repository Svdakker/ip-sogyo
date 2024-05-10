package nl.sogyo.modelr.entities

import jakarta.persistence.*

@Entity
class Centrifugation(
    @Column(nullable = false)
    var position: Int, //Position in the cascade

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "request_id", nullable = false)
    var request: CentrifugationRequest,

    @Column(columnDefinition = "TEXT", nullable = true)
    var result: String? = null,

    @ManyToOne(optional = false, cascade = [CascadeType.ALL])
    @JoinColumn(name = "cost_id", nullable = false)
    var costFactor: CostFactor, //Foreign key to CostData

    @ManyToOne(optional = false, cascade = [CascadeType.ALL])
    @JoinColumn(name = "centrifuge_id", nullable = false)
    var centrifuge: Centrifuge, //Foreign key to CultivationData

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
)