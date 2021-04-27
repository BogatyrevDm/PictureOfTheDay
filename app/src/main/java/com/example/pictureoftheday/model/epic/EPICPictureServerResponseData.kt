package com.example.pictureoftheday.model.epic

import com.google.gson.annotations.SerializedName

data class EPICPictureServerResponseData(
    @field:SerializedName("image") val date: String?
)
