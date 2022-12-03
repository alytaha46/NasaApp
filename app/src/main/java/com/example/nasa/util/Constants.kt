package com.example.nasa.util

import com.example.nasa.BuildConfig

object Constants {
    const val API_QUERY_DATE_FORMAT = "yyyy-MM-dd"
    const val DEFAULT_END_DATE_DAYS = 7
    const val BASE_URL = "https://api.nasa.gov/"
    const val API_KEY = BuildConfig.NASA_API_KEY;
}
