package com.example.nasa.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.nasa.database.model.AsteroidsRoom

@Dao
interface AsteroidsDao {
    @Query("select * from AsteroidsRoom")
    fun getAsteroids(): LiveData<List<AsteroidsRoom>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAsteroids(vararg: AsteroidsRoom)
}