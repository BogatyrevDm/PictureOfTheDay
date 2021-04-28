package com.example.pictureoftheday.utils

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


