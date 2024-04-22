package nl.sogyo.modelr.models

import com.fasterxml.jackson.annotation.JsonProperty

data class SimulationRequestDTO (
    @JsonProperty
    var operationType: String,
    @JsonProperty
    var cultivationSettings: CultivationSettingsDTO,
    @JsonProperty
    var reactorSettings: ReactorSettingsDTO,
)