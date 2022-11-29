package com.example.nasa.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.nasa.domain.Asteroids

@Entity
data class AsteroidsRoom(
    @PrimaryKey val id: String,
    val absolute_magnitude: Double,
    val estimated_diameter_max: Double,
    val is_potentially_hazardous_asteroid: Boolean,
    val kilometers_per_second: Double,
    val astronomical: Double
)

fun List<AsteroidsRoom>.asDomainModel(): List<Asteroids> {
    return map {
        Asteroids(
            id = it.id,
            absolute_magnitude = it.absolute_magnitude,
            estimated_diameter_max = it.estimated_diameter_max,
            is_potentially_hazardous_asteroid = it.is_potentially_hazardous_asteroid,
            kilometers_per_second = it.kilometers_per_second,
            astronomical = it.astronomical
        )
    }
}


