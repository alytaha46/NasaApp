package com.example.nasa.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.nasa.R
import com.example.nasa.domain.Asteroids
import com.example.nasa.repository.Repository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import timber.log.Timber

enum class ImageApiStatus { LOADING, ERROR, DONE }

class MainViewModel(private val repository: Repository) : ViewModel() {

    private val _imageStatus = MutableLiveData<ImageApiStatus>()
    val imageStatus: LiveData<ImageApiStatus> get() = _imageStatus
    val allAsteroids = repository.allAsteroids
    val weekAsteroids = repository.weekAsteroids
    val todayAsteroids = repository.todayAsteroids
    val asteroids = MutableLiveData<LiveData<List<Asteroids>>>()

    val image = repository.imageOfTheDay

    init {
        asteroids.value = allAsteroids
        viewModelScope.launch {
            async {
                try {
                    repository.refreshAsteroids()
                } catch (e: Exception) {
                    Timber.e("refreshAsteroids: ${e.message}")
                }
            }
            async {
                _imageStatus.value = ImageApiStatus.LOADING
                try {
                    repository.refreshImageOfTheDay()
                    _imageStatus.value = ImageApiStatus.DONE
                } catch (e: Exception) {
                    _imageStatus.value = ImageApiStatus.ERROR
                    Timber.e("Image Exception: ${e.message}")
                }
            }
        }
    }

    fun updateLiveData(id: Int) {
        when (id) {
            R.id.show_all_asteroids -> {
                asteroids.value = allAsteroids
            }
            R.id.show_week_asteroids -> {
                asteroids.value = weekAsteroids
            }
            R.id.show_today_asteroids -> {
                asteroids.value = todayAsteroids
            }
        }
    }
}