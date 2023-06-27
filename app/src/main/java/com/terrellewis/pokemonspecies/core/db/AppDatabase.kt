package com.terrellewis.pokemonspecies.core.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.terrellewis.pokemonspecies.core.db.AppDatabase.Companion.DATABASE_VERSION
import com.terrellewis.pokemonspecies.species.data.local.dao.SpeciesDao
import com.terrellewis.pokemonspecies.species.data.local.model.SpeciesEntity

@Database(
    entities = [SpeciesEntity::class],
    version = DATABASE_VERSION
)
abstract class AppDatabase : RoomDatabase() {
    abstract val speciesDao: SpeciesDao

    companion object {
        const val DATABASE_NAME = "pokemon_db"
        const val DATABASE_VERSION = 1
    }
}