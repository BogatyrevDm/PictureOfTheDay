package com.example.pictureoftheday.model.epic

import com.google.gson.annotations.SerializedName

data class EPICMainServerResponseData(
    @field:SerializedName("date") val date: String?
)
