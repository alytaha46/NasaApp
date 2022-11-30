package com.example.nasa.network


import com.example.nasa.constants.Constants.API_KEY
import com.example.nasa.constants.Constants.BASE_URL
import com.example.nasa.network.dataTransfareObject.ImageofTheDayResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET


interface GetFromAPI {
    @GET("planetary/apod/?api_key=$API_KEY")
    fun getImageOfTheDay(): Deferred<ImageofTheDayResponse>

    @GET("neo/rest/v1/feed?api_key=$API_KEY")
    fun getAsteroids():Deferred<String>
}


private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

object Network {
    // Configure retrofit to parse JSON and use coroutines
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val networkCall = retrofit.create(GetFromAPI::class.java)
}