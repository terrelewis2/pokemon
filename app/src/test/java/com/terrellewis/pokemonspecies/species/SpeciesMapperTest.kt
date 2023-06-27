package com.terrellewis.pokemonspecies.species

import com.terrellewis.pokemonspecies.species.data.local.mappers.extractIdFromUrl
import com.terrellewis.pokemonspecies.species.data.local.mappers.toSpecies
import com.terrellewis.pokemonspecies.species.data.local.mappers.toSpeciesEntity
import com.terrellewis.pokemonspecies.species.data.local.model.SpeciesEntity
import com.terrellewis.pokemonspecies.species.data.remote.model.SpeciesDto
import com.terrellewis.pokemonspecies.species.domain.model.Species
import org.junit.Assert.assertEquals
import org.junit.Test

class SpeciesMapperTest {

    @Test
    fun testToSpeciesEntity() {
        val speciesDto = SpeciesDto(name = "Test Species", url = "http://test.com/123/")
        val expectedEntity =
            SpeciesEntity(id = 123, name = "Test Species", url = "http://test.com/123/")

        val resultEntity = speciesDto.toSpeciesEntity()

        assertEquals(expectedEntity, resultEntity)
    }

    @Test
    fun testToSpecies() {
        val speciesEntity =
            SpeciesEntity(id = 123, name = "Test Species", url = "http://test.com/123/")
        val expectedSpecies = Species(id = 123, name = "Test Species", url = "http://test.com/123/")

        val resultSpecies = speciesEntity.toSpecies()

        assertEquals(expectedSpecies, resultSpecies)
    }

    @Test
    fun testExtractIdFromUrl() {
        val url = "http://test.com/123/"
        val expectedId = "123"

        val resultId = url.extractIdFromUrl()

        assertEquals(expectedId, resultId)
    }

    @Test
    fun testExtractIdFromUrlWithNoId() {
        val url = "http://test.com/"
        val expectedId: String? = null

        val resultId = url.extractIdFromUrl()

        assertEquals(expectedId, resultId)
    }
}