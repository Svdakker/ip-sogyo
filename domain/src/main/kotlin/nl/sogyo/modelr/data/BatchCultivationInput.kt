package nl.sogyo.modelr.data

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class BatchCultivationInput(
        val cultivationSettings: CultivationSettings,
        val reactorSettings: ReactorSettings,
        val reaction: ReactionProperties = ReactionProperties()
)
