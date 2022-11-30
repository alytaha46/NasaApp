package com.example.nasa.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.nasa.domain.ImageOfTheDay
import java.util.*

@Entity
data class ImageOfTheDayRoom(
    @PrimaryKey
    val url: String,
    val media_type: String,
    val date: Date,
    val title: String
)

fun List<ImageOfTheDayRoom>.asDomainModel(): List<ImageOfTheDay> {
    return map {
        ImageOfTheDay(url = it.url, media_type = it.media_type, title = it.title)
    }
}





