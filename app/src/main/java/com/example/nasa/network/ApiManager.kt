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


interface GetFromAPI {
    @GET("planetary/apod/?api_key=$API_KEY")
    fun getImageOfTheDay(): Deferred<ImageofTheDayResponse>

    @GET("neo/rest/v1/feed?api_key=$API_KEY")
    fun getAsteroids(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String
    ): Deferred<String>
}


private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
    .connectTimeout(1, TimeUnit.MINUTES)
    .readTimeout(1, TimeUnit.MINUTES)
    .writeTimeout(1, TimeUnit.MINUTES)
    .build()

object Network {
    // Configure retrofit to parse JSON and use coroutines
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val networkCall = retrofit.create(GetFromAPI::class.java)
}