package com.example.nasa.util

import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


object Dates {
    fun getNextSevenDaysFormattedDates(): ArrayList<String> {
        val formattedDateList = ArrayList<String>()

        val calendar = Calendar.getInstance()
        for (i in 0..Constants.DEFAULT_END_DATE_DAYS) {
            val currentTime = calendar.time
            val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
            formattedDateList.add(dateFormat.format(currentTime))
            calendar.add(Calendar.DAY_OF_YEAR, 1)
        }

        return formattedDateList
    }

    fun getTodayDate(): String {
        return getNextSevenDaysFormattedDates()[0]
    }

    fun getLastDayDate(): String {
        val week = getNextSevenDaysFormattedDates()
        return week[week.size - 1]
    }
}
