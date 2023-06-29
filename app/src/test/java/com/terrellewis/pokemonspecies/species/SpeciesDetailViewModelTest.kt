package com.terrellewis.pokemonspecies.species

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.terrellewis.pokemonspecies.core.resources.LoadableData
import com.terrellewis.pokemonspecies.species.domain.repository.SpeciesRepository
import com.terrellewis.pokemonspecies.species.presentation.species_detail.SpeciesDetailViewModel
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SpeciesDetailViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val speciesRepository: SpeciesRepository = mockk()

    private lateinit var speciesDetailViewModel: SpeciesDetailViewModel

    private val testScheduler = TestScheduler()

    @Before
    fun setUp() {
        RxJavaPlugins.setIoSchedulerHandler { testScheduler }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        speciesDetailViewModel = SpeciesDetailViewModel(speciesRepository)
    }

    @Test
    fun `getSpeciesDetailAndFirstEvolution completes successfully`() {
        val speciesId = 1
        val speciesDetail = getSampleSpeciesDetail(1, 1)

        val speciesEvolutionChain = getSampleSpeciesEvolutionChain(1, 1, 2)
        val evolutionSpeciesDetail =
            getSampleSpeciesDetail(2, 1)

        every { speciesRepository.getSpeciesDetail(1) } returns Single.just(speciesDetail)
        every { speciesRepository.getEvolutionChain(any(), any()) } returns Single.just(
            speciesEvolutionChain
        )
        every { speciesRepository.getSpeciesDetail(2) } returns Single.just(
            evolutionSpeciesDetail
        )

        speciesDetailViewModel.getSpeciesDetailAndFirstEvolution(speciesId)
        testScheduler.triggerActions()

        speciesDetailViewModel.speciesDetail.observeForever { result ->
            assertEquals(speciesDetail, (result as LoadableData.Success).data)
        }
    }

    @Test
    fun `getSpeciesDetailAndFirstEvolution throws error on getSpeciesDetail`() {
        val speciesId = 1
        val error = Throwable()

        every { speciesRepository.getSpeciesDetail(speciesId) } returns Single.error(error)

        speciesDetailViewModel.getSpeciesDetailAndFirstEvolution(speciesId)
        testScheduler.triggerActions()

        speciesDetailViewModel.speciesDetail.observeForever { result ->
            assert(result is LoadableData.Error)
            assertEquals(error, (result as LoadableData.Error).exception)
        }
    }

    @Test
    fun `getSpeciesDetailAndFirstEvolution returns null for evolves to indicate species is at end of evolution chain`() {
        val speciesId = 1
        val speciesDetail = getSampleSpeciesDetail(1, 1)
        val speciesEvolutionChain = getNullEvolvesToEvolutionChain(1, 1)

        every { speciesRepository.getSpeciesDetail(1) } returns Single.just(speciesDetail)
        every { speciesRepository.getEvolutionChain(any(), any()) } returns Single.just(
            speciesEvolutionChain
        )
        speciesDetailViewModel.getSpeciesDetailAndFirstEvolution(speciesId)
        testScheduler.triggerActions()

        speciesDetailViewModel.speciesDetail.observeForever { result ->
            assertEquals(speciesDetail, (result as LoadableData.Success).data)
        }
    }

    @After
    fun tearDown() {
        RxAndroidPlugins.reset()
        RxJavaPlugins.reset()
        clearAllMocks()
    }
}
