package com.terrellewis.pokemonspecies.species

import com.terrellewis.pokemonspecies.species.data.remote.model.EvolutionChainDto
import com.terrellewis.pokemonspecies.species.data.remote.model.SpeciesDto
import com.terrellewis.pokemonspecies.species.domain.model.Species
import com.terrellewis.pokemonspecies.species.domain.model.SpeciesDetail
import com.terrellewis.pokemonspecies.species.domain.model.SpeciesEvolutionChain


fun getSampleSpeciesDetail(speciesId: Int, chainId: Int): SpeciesDetail {
    return SpeciesDetail(
        id = speciesId,
        name = "name-$speciesId",
        flavorText = "",
        captureRate = 0,
        evolutionChainUrl = "chain-$chainId",
        evolvesTo = null,
        captureRateDifference = 0
    )
}

fun getSampleSpeciesEvolutionChain(
    chainId: Int,
    speciesId: Int,
    evolvesToId: Int
): SpeciesEvolutionChain {
    return SpeciesEvolutionChain(
        id = chainId,
        speciesId = speciesId,
        evolvesTo = Species(
            evolvesToId,
            "name-$evolvesToId",
            url = "https://pokeapi.co/api/v2/pokemon-species/$evolvesToId/"
        )
    )
}

fun getNullEvolvesToEvolutionChain(
    chainId: Int,
    speciesId: Int,
): SpeciesEvolutionChain {
    return SpeciesEvolutionChain(
        id = chainId,
        speciesId = speciesId,
        evolvesTo = null
    )
}

fun getSampleEvolutionChain(): EvolutionChainDto {
    val charmanderDto = SpeciesDto("charmander", "https://pokeapi.co/api/v2/pokemon-species/6/")
    val charmeleonDto = SpeciesDto("charmeleon", "https://pokeapi.co/api/v2/pokemon-species/5/")
    val charizardDto = SpeciesDto("charizard", "https://pokeapi.co/api/v2/pokemon-species/6/")

    return EvolutionChainDto(
        2, EvolutionChainDto.Chain(
            evolvesTo = listOf(
                EvolutionChainDto.Chain(
                    evolvesTo = listOf(
                        EvolutionChainDto.Chain(
                            evolvesTo = emptyList(),
                            species = charizardDto
                        )
                    ),
                    species = charmeleonDto
                )
            ),
            species = charmanderDto
        )
    )
}
