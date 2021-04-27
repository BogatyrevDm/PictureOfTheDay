package com.example.pictureoftheday.model.epic

sealed class EPICMainData {
    data class Success(val mainServerData: List<EPICMainServerResponseData>) : EPICMainData()
    data class Error(val error: Throwable) : EPICMainData()
    data class Loading(val progress: Int?) : EPICMainData()
}
