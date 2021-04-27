package com.example.pictureoftheday.model.pod

sealed class PODData {
    data class Success(val serverData: PODServerResponseData) : PODData()
    data class Error(val error: Throwable) : PODData()
    data class Loading(val progress: Int?) : PODData()
}
