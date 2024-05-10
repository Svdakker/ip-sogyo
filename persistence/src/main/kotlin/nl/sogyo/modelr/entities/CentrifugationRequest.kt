package nl.sogyo.modelr.entities

import jakarta.persistence.*

@Entity
class CentrifugationRequest(
    @Column(nullable = false)
    var operationType: String,

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "centrifugationSettings_id", nullable = false)
    var centrifugationSettings: CentrifugationSettings,

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "cultivationSettings_id", nullable = false)
    var centrifugeProperties: CentrifugeProperties,

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
)