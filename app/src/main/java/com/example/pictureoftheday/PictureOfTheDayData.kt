package com.example.pictureoftheday

sealed class PictureOfTheDayData {
    data class Success(val serverData: PODServerResponseData) : PictureOfTheDayData()
    data class Error(val error: Throwable) : PictureOfTheDayData()
    data class Loading(val progress: Int?) : PictureOfTheDayData()
}
