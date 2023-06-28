package com.terrellewis.pokemonspecies.species.data.mappers

import com.terrellewis.pokemonspecies.species.data.remote.model.EvolutionChainDto
import com.terrellewis.pokemonspecies.species.data.remote.model.SpeciesDetailDto
import com.terrellewis.pokemonspecies.species.data.remote.model.SpeciesDto
import com.terrellewis.pokemonspecies.species.domain.model.SpeciesDetail
import com.terrellewis.pokemonspecies.species.domain.model.SpeciesEvolutionChain

fun SpeciesDetailDto.toSpeciesDetail(): SpeciesDetail {

    return SpeciesDetail(
        id = id,
        name = name,
        flavorText = flavorTextEntries.firstOrNull {
            it.language?.name.equals("en")
        }?.flavorText ?: "",
        evolutionChainUrl = evolutionChain?.url ?: "",
        captureRate = captureRate,
        evolvesTo = null,
        captureRateDifference = 0
    )
}

fun EvolutionChainDto.toSpeciesEvolutionChain(name: String): SpeciesEvolutionChain {
    val evolvesToSpecies = findEvolutionSpecies(name, chain)?.toSpecies()
    return SpeciesEvolutionChain(
        id = id,
        speciesId = chain.species.url.extractIdFromUrl(),
        evolvesTo = evolvesToSpecies
    )
}

/**
 * Finds the species that the current species evolves to by traversing the evolution chain
 */
fun findEvolutionSpecies(currentSpeciesName: String, chain: EvolutionChainDto.Chain): SpeciesDto? {
    if (chain.species.name == currentSpeciesName) {
        return chain.evolvesTo.getOrNull(0)?.species
    } else {
        for (evolvesToChain in chain.evolvesTo) {
            val evolutionSpecies = findEvolutionSpecies(currentSpeciesName, evolvesToChain)
            if (evolutionSpecies != null) {
                return evolutionSpecies
            }
        }
    }
    return null
}