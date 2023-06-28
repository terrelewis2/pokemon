package com.terrellewis.pokemonspecies.species.domain.model


data class SpeciesEvolutionChain(
    val id: Int,
    val speciesId: Int,
    val evolvesTo: Species?
)
