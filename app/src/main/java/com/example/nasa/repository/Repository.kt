package com.example.nasa.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.nasa.util.Constants
import com.example.nasa.database.NasaDatabase
import com.example.nasa.database.model.asDomainModel
import com.example.nasa.database.model.AsteroidsRoom
import com.example.nasa.domain.Asteroids
import com.example.nasa.domain.ImageOfTheDay
import com.example.nasa.network.Network
import com.example.nasa.network.dataTransfareObject.ImageofTheDayResponse
import com.example.nasa.network.dataTransfareObject.asDatabaseModel
import com.example.nasa.network.dataTransfareObject.parseAsteroidsJsonResult
import com.example.nasa.util.Dates.getLastDayDate
import com.example.nasa.util.Dates.getTodayDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class Repository(private val database: NasaDatabase) {

    val allAsteroids: LiveData<List<Asteroids>> =
        Transformations.map(database.asteroidsDao.getAllAsteroids(getTodayLong()))
        {
            it.asDomainModel()
        }
    val todayAsteroids: LiveData<List<Asteroids>> =
        Transformations.map(database.asteroidsDao.getTodayAsteroids(getTodayLong()))
        {
            it.asDomainModel()
        }
    val weekAsteroids: LiveData<List<Asteroids>> =
        Transformations.map(database.asteroidsDao.getWeekAsteroids(getTodayLong()))
        {
            it.asDomainModel()
        }

    val imageOfTheDay: LiveData<List<ImageOfTheDay>> =
        Transformations.map(database.imageOfTheDayDao.getImage())
        {
            it.asDomainModel()
        }

    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {
            val asteroidsList = getAsteroidsFromApi()
            cacheAsteroidsToRoom(asteroidsList)
            deleteOldAsteroids(getTodayLong())
        }
    }

    private suspend fun cacheAsteroidsToRoom(list: List<AsteroidsRoom>) {
        database.asteroidsDao.insertAsteroids(list)
    }

    private suspend fun getAsteroidsFromApi(): List<AsteroidsRoom> {
        val asteroidsList = withContext(Dispatchers.IO) {
            val response: String =
                Network.networkCall.getAsteroids(
                    getTodayDate(),
                    getLastDayDate()
                ).await()
            val jsonResponse: JSONObject = JSONObject(response)
            return@withContext parseAsteroidsJsonResult(jsonResponse)
        }
        return asteroidsList
    }

    private suspend fun deleteOldAsteroids(date: Long) {
        database.asteroidsDao.deleteOldAsteroids(date)
    }

    suspend fun refreshImageOfTheDay() {
        withContext(Dispatchers.IO) {
            val response: ImageofTheDayResponse = Network.networkCall.getImageOfTheDay().await()
            if (response.mediaType == "image") {
                database.imageOfTheDayDao.insertImage(response.asDatabaseModel())
                database.imageOfTheDayDao.deleteOldImage()
            }

        }
    }

    private fun getTodayLong(): Long {
        val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
        val todayString = getTodayDate()
        return dateFormat.parse(todayString).time
    }
}