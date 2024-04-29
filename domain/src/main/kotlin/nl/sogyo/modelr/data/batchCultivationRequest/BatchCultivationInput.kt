package nl.sogyo.modelr.data.batchCultivationRequest

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class BatchCultivationInput(
        val cultivationSettings: CultivationSettings,
        val reactorSettings: ReactorSettings,
        val reaction: ReactionProperties = ReactionProperties()
)
