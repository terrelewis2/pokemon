package com.terrellewis.pokemonspecies.di

import android.content.Context
import androidx.room.Room
import com.terrellewis.pokemonspecies.core.db.AppDatabase
import com.terrellewis.pokemonspecies.species.data.SpeciesRepositoryImpl
import com.terrellewis.pokemonspecies.species.data.remote.api.PokemonApi
import com.terrellewis.pokemonspecies.species.data.remote.api.PokemonApi.Companion.BASE_URL
import com.terrellewis.pokemonspecies.species.domain.repository.SpeciesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext applicationContext: Context,
    ) = Room.databaseBuilder(
        applicationContext,
        AppDatabase::class.java,
        AppDatabase.DATABASE_NAME
    ).build()

    @Provides
    @Singleton
    fun providePokemonApi(): PokemonApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(PokemonApi::class.java)
    }

    @Provides
    fun provideSpeciesRepository(
        appDatabase: AppDatabase,
        pokemonApi: PokemonApi
    ): SpeciesRepository {
        return SpeciesRepositoryImpl(appDatabase, pokemonApi)
    }
}