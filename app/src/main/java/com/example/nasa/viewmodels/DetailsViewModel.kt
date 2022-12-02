package com.example.nasa.viewmodels


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.nasa.domain.Asteroids

class DetailsViewModel(asteroids: Asteroids) : ViewModel() {
    private val _asteroids = MutableLiveData(asteroids)
    val asteroids: LiveData<Asteroids> get() = _asteroids

    class DetailsViewModelFactory(val asteroids: Asteroids) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return DetailsViewModel(asteroids) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}