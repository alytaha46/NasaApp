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
    val id: String,
    val absolute_magnitude: Double,
    val estimated_diameter_max: Double,
    val is_potentially_hazardous_asteroid: Boolean,
    val kilometers_per_second: Double,
    val astronomical: Double
) : Parcelable