package com.terrellewis.pokemonspecies.species

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.terrellewis.pokemonspecies.core.db.AppDatabase
import com.terrellewis.pokemonspecies.species.data.SpeciesRemoteMediator
import com.terrellewis.pokemonspecies.species.data.local.model.SpeciesEntity
import com.terrellewis.pokemonspecies.species.data.remote.api.PokemonApi
import com.terrellewis.pokemonspecies.species.data.remote.model.SpeciesApiResponse
import com.terrellewis.pokemonspecies.species.data.remote.model.SpeciesDto
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class SpeciesRemoteMediatorTest {

    private lateinit var speciesRemoteMediator: SpeciesRemoteMediator
    private lateinit var appDatabase: AppDatabase
    private lateinit var pokemonApi: PokemonApi

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        pokemonApi = mockk()
    }

    @After
    fun tearDown() {
        appDatabase.close()
    }

    @OptIn(ExperimentalPagingApi::class)
    @Test
    fun refreshLoadReturnsSuccessResultWhenMoreDataIsPresent() {
        val speciesEntityList = listOf(
            SpeciesDto(name = "Species1", url = "url1"),
            SpeciesDto(name = "Species2", url = "url2")
        )
        every { pokemonApi.getSpecies(any(), any()) } returns Single.just(
            SpeciesApiResponse(
                count = speciesEntityList.size,
                next = null,
                previous = null,
                results = speciesEntityList
            )
        )
        speciesRemoteMediator = SpeciesRemoteMediator(appDatabase, pokemonApi)
        val pagingState = PagingState<Int, SpeciesEntity>(
            listOf(),
            null,
            PagingConfig(10),
            10
        )
        speciesRemoteMediator.loadSingle(LoadType.REFRESH, pagingState)
            .test()
            .await()
            .assertValueCount(1)
            .assertValue { it is RemoteMediator.MediatorResult.Success && !it.endOfPaginationReached }
    }

    @OptIn(ExperimentalPagingApi::class)
    @Test
    fun refreshLoadReturnsSuccessResultAndEndOfPaginationReachedWhenNoMoreData() {
        every { pokemonApi.getSpecies(any(), any()) } returns Single.just(
            SpeciesApiResponse(
                count = 0,
                next = null,
                previous = null,
                results = emptyList()
            )
        )
        speciesRemoteMediator = SpeciesRemoteMediator(appDatabase, pokemonApi)
        val pagingState = PagingState<Int, SpeciesEntity>(
            listOf(),
            null,
            PagingConfig(10),
            10
        )
        speciesRemoteMediator.loadSingle(LoadType.REFRESH, pagingState)
            .test()
            .await()
            .assertValueCount(1)
            .assertValue { it is RemoteMediator.MediatorResult.Success && it.endOfPaginationReached }
    }

    @OptIn(ExperimentalPagingApi::class)
    @Test
    fun refreshLoadReturnsErrorResultWhenErrorOccurs() {
        val exception = IOException()
        every { pokemonApi.getSpecies(any(), any()) } returns Single.error(exception)
        speciesRemoteMediator = SpeciesRemoteMediator(appDatabase, pokemonApi)
        val pagingState = PagingState<Int, SpeciesEntity>(
            listOf(),
            null,
            PagingConfig(10),
            10
        )
        speciesRemoteMediator.loadSingle(LoadType.REFRESH, pagingState)
            .test()
            .await()
            .assertValueCount(1)
            .assertValue { it is RemoteMediator.MediatorResult.Error && it.throwable == exception }
    }
}