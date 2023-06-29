package com.terrellewis.pokemonspecies.species.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.rxjava2.RxRemoteMediator
import com.terrellewis.pokemonspecies.core.db.AppDatabase
import com.terrellewis.pokemonspecies.species.domain.mappers.toSpeciesEntity
import com.terrellewis.pokemonspecies.species.data.local.model.SpeciesEntity
import com.terrellewis.pokemonspecies.species.data.remote.api.PokemonApi
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

@OptIn(ExperimentalPagingApi::class)
class SpeciesRemoteMediator(
    private val appDatabase: AppDatabase,
    private val pokemonApi: PokemonApi
) : RxRemoteMediator<Int, SpeciesEntity>() {

    override fun loadSingle(
        loadType: LoadType,
        state: PagingState<Int, SpeciesEntity>
    ): Single<MediatorResult> {
        val loadKey = when (loadType) {
            LoadType.REFRESH -> 0
            LoadType.PREPEND -> return Single.just(
                (MediatorResult.Success(endOfPaginationReached = true) as MediatorResult)
            )

            LoadType.APPEND -> {
                val lastItem = state.lastItemOrNull()
                lastItem?.id ?: 0
            }
        }

        return pokemonApi.getSpecies(
            offset = loadKey,
            limit = state.config.pageSize
        ).flatMap { speciesResponse ->
            // Store data in local DB
            appDatabase.runInTransaction {
                if (loadType == LoadType.REFRESH) {
                    appDatabase.speciesDao.clearAll()
                }
                val speciesEntities = speciesResponse.results.map { it.toSpeciesEntity() }
                appDatabase.speciesDao.upsertAll(speciesEntities)
            }
            Single.just(MediatorResult.Success(endOfPaginationReached = speciesResponse.results.isEmpty()) as MediatorResult)
        }
            .onErrorReturn { MediatorResult.Error(it) }
            .subscribeOn(Schedulers.io())
    }
}