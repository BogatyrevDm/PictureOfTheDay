package com.example.pictureoftheday.model.pod

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PODApi {
    @GET("planetary/apod")
    fun getPictureOfTheDay(
        @Query("api_key") apiKey: String,
        @Query("date") date: String,
        @Query("thumbs") thumbs: String = "true"
    ): Call<PODServerResponseData>
}