package com.terrellewis.pokemonspecies.species.presentation.species_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.map
import androidx.paging.rxjava2.cachedIn
import com.terrellewis.pokemonspecies.species.data.local.mappers.toSpecies
import com.terrellewis.pokemonspecies.species.domain.model.Species
import com.terrellewis.pokemonspecies.species.domain.repository.SpeciesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Flowable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@HiltViewModel
class SpeciesListViewModel @Inject constructor(
    private val repository: SpeciesRepository
) : ViewModel() {

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getSpeciesList(): Flowable<PagingData<Species>> {
        return repository.getSpeciesList()
            .map { pagingData ->
                pagingData.map { it.toSpecies() }
            }
            .cachedIn(viewModelScope)
    }

}