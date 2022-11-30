package com.example.nasa.network.dataTransfareObject

import com.example.nasa.database.ImageOfTheDayRoom
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Json

@JsonClass(generateAdapter = true)
data class ImageofTheDayResponse(

    @Json(name = "date")
    val date: String,

    @Json(name = "copyright")
    val copyright: String,

    @Json(name = "media_type")
    val mediaType: String,

    @Json(name = "hdurl")
    val hdurl: String,

    @Json(name = "service_version")
    val serviceVersion: String,

    @Json(name = "explanation")
    val explanation: String,

    @Json(name = "title")
    val title: String,

    @Json(name = "url")
    val url: String
)

fun ImageofTheDayResponse.asDatabaseModel(): ImageOfTheDayRoom {
    return ImageOfTheDayRoom(url, mediaType, title)
}

