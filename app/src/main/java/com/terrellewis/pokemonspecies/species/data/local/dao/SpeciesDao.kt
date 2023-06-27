package com.terrellewis.pokemonspecies.species.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.terrellewis.pokemonspecies.species.data.local.model.SpeciesEntity

@Dao
interface SpeciesDao {

    @Upsert
    fun upsertAll(species: List<SpeciesEntity>)

    @Query("SELECT * from species")
    fun pagingSource(): PagingSource<Int, SpeciesEntity>

    @Query("DELETE from species")
    fun clearAll()

}