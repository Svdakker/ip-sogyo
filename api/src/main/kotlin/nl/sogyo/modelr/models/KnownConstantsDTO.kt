package nl.sogyo.modelr.models

import com.fasterxml.jackson.annotation.JsonProperty

data class KnownConstantsDTO(
    @JsonProperty
    var microorganisms: List<String?> = emptyList(),
    @JsonProperty
    var reactors: List<String?> = emptyList(),
    @JsonProperty
    var impellers: List<String?> = emptyList()
)