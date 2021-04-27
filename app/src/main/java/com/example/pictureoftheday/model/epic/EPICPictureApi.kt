package com.example.pictureoftheday.model.epic

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface EPICPictureApi {
    @GET("EPIC/api/natural/date/{date}")
    fun getPicture(
        @Path("date") year: String,
        @Query("api_key") apiKey: String
    ): Call<List<EPICPictureServerResponseData>>
}