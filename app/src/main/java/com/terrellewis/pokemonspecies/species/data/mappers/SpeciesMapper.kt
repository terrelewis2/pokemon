package com.terrellewis.pokemonspecies.species.data.mappers

import com.terrellewis.pokemonspecies.species.data.local.model.SpeciesEntity
import com.terrellewis.pokemonspecies.species.data.remote.model.SpeciesDto
import com.terrellewis.pokemonspecies.species.domain.model.Species
import java.net.URI


fun SpeciesDto.toSpeciesEntity(): SpeciesEntity {
    return SpeciesEntity(
        id = url.extractIdFromUrl(),
        name = name,
        url = url
    )
}

fun SpeciesEntity.toSpecies(): Species {
    return Species(
        id = id,
        name = name,
        url = url
    )
}

fun SpeciesDto.toSpecies(): Species {
    return Species(
        id = url.extractIdFromUrl(),
        name = name,
        url = url
    )
}

fun String.extractIdFromUrl(): Int {
    val pattern = Regex("""/(\d+)/?$""")
    val matchResult = pattern.find(this)

    return matchResult?.groupValues?.get(1)?.toInt() ?: 0
}