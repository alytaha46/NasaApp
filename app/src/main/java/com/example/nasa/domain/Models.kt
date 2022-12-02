package com.example.nasa.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class ImageOfTheDay(
    val url: String,
    val media_type: String,
    val title: String
)
@Parcelize
data class Asteroids(
    val id: Long,
    val name:String,
    val date:String,
    val absolute_magnitude: Double,
    val estimated_diameter: Double,
    val is_hazardous: Boolean,
    val kilometers_per_second: Double,
    val distanceFromEarth: Double
) : Parcelable