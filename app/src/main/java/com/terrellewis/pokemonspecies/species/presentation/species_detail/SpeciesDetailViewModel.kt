package com.terrellewis.pokemonspecies.species.presentation.species_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.terrellewis.pokemonspecies.species.data.mappers.extractIdFromUrl
import com.terrellewis.pokemonspecies.species.domain.model.SpeciesEvolutionChain
import com.terrellewis.pokemonspecies.species.domain.model.SpeciesDetail
import com.terrellewis.pokemonspecies.species.domain.repository.SpeciesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class SpeciesDetailViewModel @Inject constructor(private val speciesRepository: SpeciesRepository) :
    ViewModel() {

    private val disposables = CompositeDisposable()

    fun getSpeciesDetailAndFirstEvolution(id: Int): LiveData<Result<SpeciesDetail?>> {
        val result = MutableLiveData<Result<SpeciesDetail?>>()

        val disposable = fetchSpeciesDetailAndEvolutionChain(id)
            .flatMap(this::fetchFirstEvolutionSpeciesDetail)
            .subscribe(
                { result.postValue(Result.success(updateSpeciesDetailWithEvolution(it))) },
                { result.postValue(Result.failure(it)) })

        disposables.add(disposable)
        return result
    }

    private fun fetchSpeciesDetailAndEvolutionChain(id: Int):
            Single<Pair<SpeciesDetail, SpeciesEvolutionChain?>> {
        return speciesRepository.getSpeciesDetail(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap { speciesDetail ->
                speciesRepository.getEvolutionChain(
                    speciesDetail.name,
                    speciesDetail.evolutionChainUrl
                )
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map { evolutionChain -> Pair(speciesDetail, evolutionChain) }
            }
    }

    private fun fetchFirstEvolutionSpeciesDetail(pair: Pair<SpeciesDetail, SpeciesEvolutionChain?>):
            Single<Triple<SpeciesDetail, SpeciesEvolutionChain?, SpeciesDetail?>> {
        return pair.second?.evolvesTo?.let { evolutionSpecies ->
            speciesRepository.getSpeciesDetail(
                evolutionSpecies.url.extractIdFromUrl()
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { firstEvolutionSpecies ->
                    Triple(
                        pair.first,
                        pair.second,
                        firstEvolutionSpecies
                    )
                }
        } ?: Single.just(Triple(pair.first, null, null))
    }

    private fun updateSpeciesDetailWithEvolution(
        triple: Triple<SpeciesDetail,
                SpeciesEvolutionChain?,
                SpeciesDetail?>
    ): SpeciesDetail {
        triple.first.evolvesTo = triple.third
        triple.first.captureRateDifference =
            triple.third?.let { triple.first.captureRate - it.captureRate } ?: 0
        return triple.first
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}