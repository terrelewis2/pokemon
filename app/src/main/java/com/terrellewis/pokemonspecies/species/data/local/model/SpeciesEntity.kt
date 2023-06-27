package com.terrellewis.pokemonspecies.species.data.local.model

import androidx.room.Entity

@Entity(tableName = "species", primaryKeys = [("id")])
data class SpeciesEntity(
    val id: Int,
    val name: String,
    val url: String
)
