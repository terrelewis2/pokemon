package com.terrellewis.pokemonspecies.species.data.local.mappers

import com.terrellewis.pokemonspecies.species.data.local.model.SpeciesEntity
import com.terrellewis.pokemonspecies.species.data.remote.model.SpeciesDto
import com.terrellewis.pokemonspecies.species.domain.model.Species
import java.net.URI


fun SpeciesDto.toSpeciesEntity(): SpeciesEntity {
    return SpeciesEntity(
        id = url.extractIdFromUrl()?.toInt() ?: 0,
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

fun String.extractIdFromUrl(): String? {
    val uri = URI(this)
    val segments = uri.path.split("/")
    val id = segments.getOrNull(segments.size - 2)
    return if (id.isNullOrEmpty()) null else id
}
