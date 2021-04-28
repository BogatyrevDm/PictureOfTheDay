package com.example.pictureoftheday

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pictureoftheday.utils.*
import com.example.pictureoftheday.view.MainFragment

class MainActivity : AppCompatActivity() {

    var themeChoosen: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        themeChoosen = getAppThemeChoosen(MAIN_THEME)
        themeChoosen?.let {
            setTheme(getAppTheme(it))
        }

        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }

    fun getAppThemeChoosen(appThemeDefault: Int): Int {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE)
        return sharedPreferences.getInt(APP_THEME_CHOOSEN, appThemeDefault)
    }

    fun setAppTheme() {
        with(getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE).edit()) {
            putInt(APP_THEME_CHOOSEN, themeChoosen!!)
            apply()
        }
    }
}