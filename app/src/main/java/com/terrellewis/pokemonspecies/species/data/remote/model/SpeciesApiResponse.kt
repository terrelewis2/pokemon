package com.terrellewis.pokemonspecies.species.data.remote.model

data class SpeciesApiResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<SpeciesDto>
)