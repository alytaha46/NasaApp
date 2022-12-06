package com.example.nasa.network.dataTransfareObject

import com.example.nasa.util.Constants
import com.example.nasa.database.model.ImageOfTheDayRoom
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Json
import java.text.SimpleDateFormat
import java.util.*

@JsonClass(generateAdapter = true)
data class ImageofTheDayResponse(

    @Json(name = "date")
    val date: String,

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
    val formatter = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
    val dateParsed: Date = formatter.parse(date) as Date
    return ImageOfTheDayRoom(url, mediaType, dateParsed, title)
}

