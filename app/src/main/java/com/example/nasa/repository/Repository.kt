package com.example.nasa.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
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

class Repository(private val database: NasaDatabase) {

    val asteroids: LiveData<List<Asteroids>> =
        Transformations.map(database.AsteroidsDao.getAsteroids())
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
        }
    }

    suspend fun refreshImageOfTheDay() {
        withContext(Dispatchers.IO) {
            val response: ImageofTheDayResponse = Network.networkCall.getImageOfTheDay().await()
            database.ImageOfTheDayDao.insertImage(response.asDatabaseModel())
        }
    }
}