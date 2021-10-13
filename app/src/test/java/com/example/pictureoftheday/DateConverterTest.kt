package com.example.pictureoftheday

import com.example.pictureoftheday.model.Days
import com.example.pictureoftheday.utils.getStringDateFromEnum
import org.junit.Assert.assertNotNull
import org.junit.Test

class DateConverterTest {
    @Test
    fun dateConverter_getStringDateFromEnum_ReturnsNotNull() {
        assertNotNull(getStringDateFromEnum(Days.TODAY))
    }

    @Test
    fun dateConverter_getStringDateWithMonthFromEnum_ReturnsNotNull() {
        assertNotNull(getStringDateFromEnum(Days.YESTERDAY))
    }
}