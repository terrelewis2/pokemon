package com.terrellewis.pokemonspecies.species.domain.model


data class SpeciesEvolutionChain(
    val id: Int, //id of the chain
    val speciesId: Int, //id of the current species
    val evolvesTo: Species? //species that current species can evolve to, null in case species is in the end of evolution chain
)
