package com.example.nasa.database.model

import androidx.room.TypeConverter
import java.util.*


class DateConverter {
    @TypeConverter
    fun toTimeStamp(date: Date): Long {
        return date.time
    }

    @TypeConverter
    fun toDate(time: Long): Date {
        return Date(time)
    }
}