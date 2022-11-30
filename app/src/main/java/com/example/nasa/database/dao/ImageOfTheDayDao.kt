package com.example.nasa.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.nasa.database.ImageOfTheDayRoom

@Dao
interface ImageOfTheDayDao {
    @Query("select * from ImageOfTheDayRoom ORDER BY date DESC LIMIT 1")
    fun getImage(): LiveData<List<ImageOfTheDayRoom>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertImage(vararg img: ImageOfTheDayRoom)
}