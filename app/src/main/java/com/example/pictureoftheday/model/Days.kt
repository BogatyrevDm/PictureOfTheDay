package com.example.pictureoftheday.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
enum class Days:Parcelable {
    DAYBEFOREYESTERDAY, YESTERDAY, TODAY
}