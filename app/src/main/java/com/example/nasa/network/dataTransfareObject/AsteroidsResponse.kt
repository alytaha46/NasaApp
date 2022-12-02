package com.example.nasa.network.dataTransfareObject

import com.example.nasa.database.model.AsteroidsRoom
import com.example.nasa.util.Constants
import com.example.nasa.util.Dates.getNextSevenDaysFormattedDates
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

fun parseAsteroidsJsonResult(jsonResult: JSONObject): ArrayList<AsteroidsRoom> {
    val nearEarthObjectsJson = jsonResult.getJSONObject("near_earth_objects")

    val asteroidList = ArrayList<AsteroidsRoom>()

    val nextSevenDaysFormattedDates = getNextSevenDaysFormattedDates()
    for (formattedDate in nextSevenDaysFormattedDates) {
        if (nearEarthObjectsJson.has(formattedDate)) {
            val dateAsteroidJsonArray = nearEarthObjectsJson.getJSONArray(formattedDate)

            for (i in 0 until dateAsteroidJsonArray.length()) {
                val asteroidJson = dateAsteroidJsonArray.getJSONObject(i)
                val id = asteroidJson.getLong("id")
                val codename = asteroidJson.getString("name")
                val formatter =
                    SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
                val date: Date = formatter.parse(formattedDate)!!
                val absoluteMagnitude = asteroidJson.getDouble("absolute_magnitude_h")
                val estimatedDiameter = asteroidJson.getJSONObject("estimated_diameter")
                    .getJSONObject("kilometers").getDouble("estimated_diameter_max")
                val closeApproachData = asteroidJson
                    .getJSONArray("close_approach_data").getJSONObject(0)
                val relativeVelocity = closeApproachData.getJSONObject("relative_velocity")
                    .getDouble("kilometers_per_second")
                val distanceFromEarth = closeApproachData.getJSONObject("miss_distance")
                    .getDouble("astronomical")
                val isPotentiallyHazardous = asteroidJson
                    .getBoolean("is_potentially_hazardous_asteroid")

                val asteroid = AsteroidsRoom(
                    id,
                    codename,
                    date,
                    absoluteMagnitude,
                    estimatedDiameter,
                    isPotentiallyHazardous,
                    relativeVelocity,
                    distanceFromEarth
                )
                asteroidList.add(asteroid)
            }
        }
    }

    return asteroidList
}


