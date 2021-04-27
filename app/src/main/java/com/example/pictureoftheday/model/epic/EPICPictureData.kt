package com.example.pictureoftheday.model.epic

sealed class EPICPictureData {
    data class Success(val mainServerData: List<EPICPictureServerResponseData>) : EPICPictureData()
    data class Error(val error: Throwable) : EPICPictureData()
    data class Loading(val progress: Int?) : EPICPictureData()
}
