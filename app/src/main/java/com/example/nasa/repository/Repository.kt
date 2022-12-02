package com.example.nasa.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.nasa.constants.Constants
import com.example.nasa.database.NasaDatabase
import com.example.nasa.database.asDomainModel
import com.example.nasa.database.model.AsteroidsRoom
import com.example.nasa.database.model.asDomainModel
import com.example.nasa.domain.Asteroids
import com.example.nasa.domain.ImageOfTheDay
import com.example.nasa.network.Network
import com.example.nasa.network.dataTransfareObject.ImageofTheDayResponse
import com.example.nasa.network.dataTransfareObject.asDatabaseModel
import com.example.nasa.network.dataTransfareObject.parseAsteroidsJsonResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class Repository(private val database: NasaDatabase) {

    val asteroids: LiveData<List<Asteroids>> =
        Transformations.map(database.AsteroidsDao.getAsteroids(getTodayLong()))
        {
            it.asDomainModel()
        }

    val imageOfTheDay: LiveData<List<ImageOfTheDay>> =
        Transformations.map(database.ImageOfTheDayDao.getImage())
        {
            it.asDomainModel()
        }

    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {
            val response: String = Network.networkCall.getAsteroids().await()
            val jsonResponse: JSONObject = JSONObject(response)
            val asteroidsList: List<AsteroidsRoom> = parseAsteroidsJsonResult(jsonResponse)
            database.AsteroidsDao.insertAsteroids(asteroidsList)
            database.AsteroidsDao.deleteOldAsteroids(getTodayLong())
        }
    }

    suspend fun refreshImageOfTheDay() {
        withContext(Dispatchers.IO) {
            val response: ImageofTheDayResponse = Network.networkCall.getImageOfTheDay().await()
            if (response.mediaType == "image") {
                database.ImageOfTheDayDao.insertImage(response.asDatabaseModel())
                database.ImageOfTheDayDao.deleteOldImage()
            }

        }
    }

    fun getTodayLong(): Long {
        val currentTime = Calendar.getInstance()
        val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
        val todayString = dateFormat.format(currentTime.time)
        return dateFormat.parse(todayString).time
    }
}