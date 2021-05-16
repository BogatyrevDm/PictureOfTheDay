package com.example.pictureoftheday.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import com.example.pictureoftheday.R

const val APP_THEME_CHOOSEN = "APP_THEME_CHOOSEN"
const val MAIN_THEME = 0
const val RED_THEME = 1
const val BLUE_THEME = 2

const val PREFERENCES_NAME = "PICTURE_OF_THE_DAY"

fun getAppTheme(themeID: Int): Int =
    when (themeID) {
        MAIN_THEME -> R.style.Theme_PictureOfTheDay
        RED_THEME -> R.style.RedTheme
        BLUE_THEME -> R.style.BlueTheme
        else -> R.style.Theme_PictureOfTheDay
    }

fun setAppTheme(themeChoosen:Int?, context: Context) {
    with(context.getSharedPreferences(PREFERENCES_NAME, AppCompatActivity.MODE_PRIVATE).edit()) {
        putInt(com.example.pictureoftheday.utils.APP_THEME_CHOOSEN, themeChoosen!!)
        apply()
    }
}

fun getAppThemeChoosen(appThemeDefault: Int, context: Context): Int {
    val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFERENCES_NAME, AppCompatActivity.MODE_PRIVATE)
    return sharedPreferences.getInt(APP_THEME_CHOOSEN, appThemeDefault)
}

