package com.terrellewis.pokemonspecies.species.data.remote.model

import com.squareup.moshi.Json

data class SpeciesDetailDto(
    val id: Int,
    val name: String,
    @field:Json(name = "capture_rate")
    val captureRate: Int,
    @field:Json(name = "evolution_chain")
    val evolutionChain: EvolutionChain?,
    @field:Json(name = "flavor_text_entries")
    val flavorTextEntries: List<FlavorTextEntry>

) {
    data class EvolutionChain(
        val url: String?
    )

    data class FlavorTextEntry(
        @field:Json(name = "flavor_text")
        val flavorText: String?,
        val language: Language?
    )

    data class Language(
        val name: String
    )
}
