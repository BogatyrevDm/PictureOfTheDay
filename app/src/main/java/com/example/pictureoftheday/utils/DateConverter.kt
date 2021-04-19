package com.example.pictureoftheday.utils

import com.example.pictureoftheday.Days
import java.text.SimpleDateFormat
import java.util.*

fun getStringDateFromEnum(day: Days): String {
    val calendar = Calendar.getInstance()
    calendar.time = calendar.getTime()
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    when (day) {
        Days.DAYBEFOREYESTERDAY -> {
            calendar.add(Calendar.DATE, -2)
        }
        Days.YESTERDAY -> {
            calendar.add(Calendar.DATE, -1)
        }
    }
    return dateFormat.format(calendar.time)
}

