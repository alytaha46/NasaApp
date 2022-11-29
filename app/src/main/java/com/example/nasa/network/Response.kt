package com.example.nasa.network

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

data class Response(


	@Json(name="hdurl")
	val hdurl: String? = null,

	@Json(name="service_version")
	val serviceVersion: String? = null,

)
