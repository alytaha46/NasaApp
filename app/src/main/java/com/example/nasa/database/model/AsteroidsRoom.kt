package com.example.nasa.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.nasa.constants.Constants
import com.example.nasa.domain.Asteroids
import java.text.SimpleDateFormat
import java.util.*


@Entity
data class AsteroidsRoom(
    @PrimaryKey val id: Long,
    val name: String,
    val date: Date,
    val absolute_magnitude: Double,
    val estimated_diameter_max: Double,
    val is_potentially_hazardous_asteroid: Boolean,
    val kilometers_per_second: Double,
    val distanceFromEarth: Double
)

fun List<AsteroidsRoom>.asDomainModel(): List<Asteroids> {
    return map {
        Asteroids(
            id = it.id,
            name = it.name,
            date = parseDateToString(it.date),
            absolute_magnitude = it.absolute_magnitude,
            estimated_diameter_max = it.estimated_diameter_max,
            is_potentially_hazardous_asteroid = it.is_potentially_hazardous_asteroid,
            kilometers_per_second = it.kilometers_per_second,
            distanceFromEarth = it.distanceFromEarth
        )
    }
}
fun parseDateToString(date:Date):String
{
    val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
    return dateFormat.format(date)
}


