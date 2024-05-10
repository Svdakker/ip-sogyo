package nl.sogyo.modelr.entities

import jakarta.persistence.*

@Entity
class BatchRequest(
    @Column(nullable = false)
    var operationType: String,

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "reactorSetttings_id", nullable = false)
    var reactorSettings: ReactorSettings,

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "cultivationSettings_id", nullable = false)
    var cultivationSettings: CultivationSettings,

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
)