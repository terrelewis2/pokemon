package com.terrellewis.pokemonspecies.species.data.remote.api

import com.terrellewis.pokemonspecies.species.data.remote.model.EvolutionChainDto
import com.terrellewis.pokemonspecies.species.data.remote.model.SpeciesApiResponse
import com.terrellewis.pokemonspecies.species.data.remote.model.SpeciesDetailDto
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface PokemonApi {
    @GET("pokemon-species")
    fun getSpecies(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Single<SpeciesApiResponse>

    companion object {
        const val BASE_URL = "https://pokeapi.co/api/v2/"
    }

    @GET("pokemon-species/{id}")
    fun getSpeciesDetail(
        @Path("id") id: Int
    ): Single<SpeciesDetailDto>

    @GET
    fun getEvolutionChain(
        @Url url: String
    ): Single<EvolutionChainDto>

}