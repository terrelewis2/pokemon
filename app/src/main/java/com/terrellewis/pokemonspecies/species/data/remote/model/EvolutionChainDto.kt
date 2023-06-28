package com.terrellewis.pokemonspecies.species.data.remote.model

import com.squareup.moshi.Json

data class EvolutionChainDto(
    val id: Int,
    val chain: Chain
) {
    data class Chain(
        @field:Json(name = "evolves_to")
        val evolvesTo: List<Chain>,
        val species: SpeciesDto
    )
}
