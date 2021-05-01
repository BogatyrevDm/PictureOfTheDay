package com.example.pictureoftheday.utils

import com.example.pictureoftheday.model.Days
import java.text.SimpleDateFormat
import java.util.*

fun getStringDateFromEnum(day: Days): String {
    return getFormatedDateFromEnum(day, "yyyy-MM-dd")
}

fun getStringDateWithMonthFromEnum(day: Days): String {
    return getFormatedDateFromEnum(day, "dd MMMM yyyy")
}

private fun getFormatedDateFromEnum(day: Days, format: String): String {
    val calendar = Calendar.getInstance()
    calendar.time = calendar.getTime()
    val dateFormat = SimpleDateFormat(format, Locale.getDefault())
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


