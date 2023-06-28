package com.terrellewis.pokemonspecies.species.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import com.terrellewis.pokemonspecies.core.db.AppDatabase
import com.terrellewis.pokemonspecies.species.data.local.model.SpeciesEntity
import com.terrellewis.pokemonspecies.species.data.mappers.toEvolutionChain
import com.terrellewis.pokemonspecies.species.data.remote.api.PokemonApi
import com.terrellewis.pokemonspecies.species.data.mappers.toSpeciesDetail
import com.terrellewis.pokemonspecies.species.domain.model.SpeciesEvolutionChain
import com.terrellewis.pokemonspecies.species.domain.model.SpeciesDetail
import com.terrellewis.pokemonspecies.species.domain.repository.SpeciesRepository
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class SpeciesRepositoryImpl @Inject constructor(
    private val appDatabase: AppDatabase,
    private val pokemonApi: PokemonApi
) : SpeciesRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getSpeciesList(): Flowable<PagingData<SpeciesEntity>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = SpeciesRemoteMediator(
                appDatabase = appDatabase,
                pokemonApi = pokemonApi
            ),
            pagingSourceFactory = {
                appDatabase.speciesDao.pagingSource()
            }
        ).flowable
    }

    override fun getSpeciesDetail(id: Int): Single<SpeciesDetail> {
        return pokemonApi.getSpeciesDetail(id).map { it.toSpeciesDetail() }
    }

    override fun getEvolutionChain(speciesName: String, url: String): Single<SpeciesEvolutionChain> {
        return pokemonApi.getEvolutionChain(url).map { it.toEvolutionChain(speciesName) }
    }
}