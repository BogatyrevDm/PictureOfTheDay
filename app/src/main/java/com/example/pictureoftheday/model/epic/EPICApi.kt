package com.example.pictureoftheday.model.epic

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface EPICApi {
    @GET("EPIC/api/natural/all")
    fun getDates(
        @Query("api_key") apiKey: String
    ): Call<List<EPICMainServerResponseData>>
}