package com.example.nasa.viewmodels


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.nasa.domain.Asteroids

class DetailsViewModel() : ViewModel() {
    private val _asteroids = MutableLiveData<Asteroids>()
    val asteroids: LiveData<Asteroids> get() = _asteroids

    fun setAsteroids(asteroids: Asteroids) {
        _asteroids.value = asteroids
    }
}
