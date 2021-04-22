package com.example.pictureoftheday

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pictureoftheday.utils.*

class MainActivity : AppCompatActivity() {

    var themeChoosen: Int? = null
    var themeSaved: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        themeSaved = getAppThemeSaved(MAIN_THEME)

        themeChoosen = getAppThemeChoosen(MAIN_THEME)
//        setTheme(getAppTheme(1))
        themeChoosen?.let {
            setTheme(getAppTheme(it))
        }

        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PictureOfTheDayFragment.newInstance())
                .commitNow()
        }
    }

    fun getAppThemeSaved(appThemeDefault: Int): Int {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE)
        return sharedPreferences.getInt(APP_THEME_SAVED, appThemeDefault)
    }

    fun getAppThemeChoosen(appThemeDefault: Int): Int {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE)
        return sharedPreferences.getInt(APP_THEME_CHOOSEN, appThemeDefault)
    }

    fun setAppTheme() {
        with(getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE).edit()) {
            putInt(APP_THEME_CHOOSEN, themeChoosen!!)
            putInt(APP_THEME_SAVED, themeSaved!!)
            apply()
        }
    }
}