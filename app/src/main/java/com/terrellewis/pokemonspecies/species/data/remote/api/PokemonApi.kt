package com.terrellewis.pokemonspecies.species.data.remote.api

import com.terrellewis.pokemonspecies.species.data.remote.model.SpeciesApiResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface PokemonApi {
    @GET("pokemon-species")
    fun getSpecies(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Single<SpeciesApiResponse>

    companion object {
        const val BASE_URL = "https://pokeapi.co/api/v2/"
    }
}