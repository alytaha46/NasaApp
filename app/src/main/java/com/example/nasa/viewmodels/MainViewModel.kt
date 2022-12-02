package com.example.nasa.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.nasa.database.getDatabase
import com.example.nasa.repository.Repository
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

enum class ImageApiStatus { LOADING, ERROR, DONE }

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _imageStatus = MutableLiveData<ImageApiStatus>()
    val imageStatus: LiveData<ImageApiStatus> get() = _imageStatus

    private val database = getDatabase(application)
    private val repository = Repository(database)

    init {
        viewModelScope.launch {
            async {
                try {
                    repository.refreshAsteroids()
                } catch (e: Exception) {
                    Log.e("Asteroids Exception", "refreshAsteroids: ${e.message}")
                }
            }
            async {
                _imageStatus.value = ImageApiStatus.LOADING
                try {
                    repository.refreshImageOfTheDay()
                    _imageStatus.value = ImageApiStatus.DONE
                } catch (e: Exception) {
                    _imageStatus.value = ImageApiStatus.ERROR
                    Log.e("Image Exception", "Image Exception: $e")
                }
            }
        }
    }

    val asteroids = repository.asteroids
    val image = repository.imageOfTheDay

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}