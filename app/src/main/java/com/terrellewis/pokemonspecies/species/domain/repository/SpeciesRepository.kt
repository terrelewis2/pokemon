package com.terrellewis.pokemonspecies.species.domain.repository

import androidx.paging.PagingData
import com.terrellewis.pokemonspecies.species.data.local.model.SpeciesEntity
import com.terrellewis.pokemonspecies.species.domain.model.SpeciesEvolutionChain
import com.terrellewis.pokemonspecies.species.domain.model.SpeciesDetail
import io.reactivex.Flowable
import io.reactivex.Single

interface SpeciesRepository {

    fun getSpeciesList(): Flowable<PagingData<SpeciesEntity>>

    fun getSpeciesDetail(id: Int): Single<SpeciesDetail>

    fun getEvolutionChain(speciesName: String, url: String): Single<SpeciesEvolutionChain>
}