package com.example.nasa.network


import com.example.nasa.network.dataTransfareObject.ImageofTheDayResponse
import com.example.nasa.util.Constants.API_KEY
import com.example.nasa.util.Constants.BASE_URL
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit


interface NasaAPI {
    @GET("planetary/apod/?api_key=$API_KEY")
    fun getImageOfTheDay(): Deferred<ImageofTheDayResponse>

    @GET("neo/rest/v1/feed?api_key=$API_KEY")
    fun getAsteroids(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String
    ): Deferred<String>
}


