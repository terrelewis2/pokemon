package com.terrellewis.pokemonspecies.species.domain.model

data class SpeciesDetail(
    val id: Int,
    val name: String,
    val flavorText: String,
    val captureRate: Int,
    val evolutionChainUrl: String,
    var evolvesTo: SpeciesDetail?,
    var captureRateDifference: Int = 0
)
