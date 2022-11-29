package com.example.nasa.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.nasa.database.ImageOfTheDayRoom

@Dao
interface ImageOfTheDayDao {
    @Query("select * from ImageOfTheDayRoom")
    fun getImage(): LiveData<List<ImageOfTheDayRoom>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImage(vararg img: ImageOfTheDayRoom)
}