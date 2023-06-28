package com.terrellewis.pokemonspecies.species

import com.terrellewis.pokemonspecies.species.data.mappers.extractIdFromUrl
import com.terrellewis.pokemonspecies.species.data.mappers.findEvolutionSpecies
import com.terrellewis.pokemonspecies.species.data.mappers.toSpeciesEvolutionChain
import com.terrellewis.pokemonspecies.species.data.mappers.toSpeciesDetail
import com.terrellewis.pokemonspecies.species.data.remote.model.EvolutionChainDto
import com.terrellewis.pokemonspecies.species.data.remote.model.SpeciesDetailDto
import com.terrellewis.pokemonspecies.species.data.remote.model.SpeciesDto
import org.junit.Assert.assertEquals
import org.junit.Test

class SpeciesDetailMapperTest {
    @Test
    fun testToSpeciesDetailMapping() {
        val flavorTextEntry =
            SpeciesDetailDto.FlavorTextEntry("flavor text", SpeciesDetailDto.Language("en"))
        val speciesDetailDto = SpeciesDetailDto(
            1,
            "name",
            100,
            SpeciesDetailDto.EvolutionChain("url"),
            listOf(flavorTextEntry)
        )

        val speciesDetail = speciesDetailDto.toSpeciesDetail()

        assertEquals(speciesDetail.id, speciesDetailDto.id)
        assertEquals(speciesDetail.name, speciesDetailDto.name)
        assertEquals(speciesDetail.flavorText, flavorTextEntry.flavorText)
        assertEquals(speciesDetail.evolutionChainUrl, speciesDetailDto.evolutionChain?.url)
        assertEquals(speciesDetail.captureRate, speciesDetailDto.captureRate)
    }

    @Test
    fun testToEvolutionChainMapping() {
        val speciesDto = SpeciesDto("name", "url")
        val chain = EvolutionChainDto.Chain(emptyList(), speciesDto)
        val evolutionChainDto = EvolutionChainDto(1, chain)

        val speciesEvolutionChain = evolutionChainDto.toSpeciesEvolutionChain("name")

        assertEquals(speciesEvolutionChain.id, evolutionChainDto.id)
        assertEquals(speciesEvolutionChain.speciesId, chain.species.url.extractIdFromUrl())
    }

    @Test
    fun testFindEvolutionSpeciesWhenEvolvesToExists() {
        val chainToEvaluate = getSampleEvolutionChain()
        val evolvesToSpeciesDto = SpeciesDto("charizard", "https://pokeapi.co/api/v2/pokemon-species/6/")

        val foundSpecies = findEvolutionSpecies("charmeleon", chainToEvaluate.chain)

        assertEquals(evolvesToSpeciesDto, foundSpecies)
    }

    @Test
    fun testFindEvolutionSpeciesWhenEvolvesToDoesNotExist() {
        val chainToEvaluate = getSampleEvolutionChain()

        val foundSpecies = findEvolutionSpecies("charizard", chainToEvaluate.chain)

        assertEquals(null, foundSpecies)
    }
}