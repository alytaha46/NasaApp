package com.example.nasa.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.nasa.database.dao.AsteroidsDao
import com.example.nasa.database.dao.ImageOfTheDayDao
import com.example.nasa.database.model.AsteroidsRoom

@Database(entities = [AsteroidsRoom::class, ImageOfTheDayRoom::class], version = 1)
abstract class NasaDatabase : RoomDatabase() {
    abstract val ImageOfTheDayDao: ImageOfTheDayDao
    abstract val AsteroidsDao: AsteroidsDao
}

private lateinit var INSTANCE: NasaDatabase

fun getDatabase(context: Context): NasaDatabase {
    synchronized(NasaDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                NasaDatabase::class.java,
                "nasa"
            ).fallbackToDestructiveMigration().build()
        }
    }
    return INSTANCE
}