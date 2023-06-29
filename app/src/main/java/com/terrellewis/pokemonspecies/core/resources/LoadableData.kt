package com.terrellewis.pokemonspecies.core.resources

sealed class LoadableData<out T> {
    object Loading : LoadableData<Nothing>()
    data class Success<out T>(val data: T) : LoadableData<T>()
    data class Error(val exception: Throwable) : LoadableData<Nothing>()
}