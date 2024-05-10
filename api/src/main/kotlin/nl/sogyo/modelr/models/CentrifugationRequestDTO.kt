package nl.sogyo.modelr.models

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.JoinColumn

data class CentrifugationRequestDTO(
    @JsonProperty
    var operationType: String,
    @JsonProperty
    var centrifugationSettings: CentrifugationSettingsDTO,
    @JoinColumn
    var centrifugeProperties: CentrifugePropertiesDTO,
)