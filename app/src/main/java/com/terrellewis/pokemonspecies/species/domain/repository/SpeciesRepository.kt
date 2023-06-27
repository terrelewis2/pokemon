package com.terrellewis.pokemonspecies.species.domain.repository

import androidx.paging.PagingData
import com.terrellewis.pokemonspecies.species.data.local.model.SpeciesEntity
import io.reactivex.Flowable

interface SpeciesRepository {

    fun getSpeciesList(): Flowable<PagingData<SpeciesEntity>>
}