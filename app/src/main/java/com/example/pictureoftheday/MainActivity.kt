package com.example.pictureoftheday

import android.os.Bundle
import com.example.pictureoftheday.utils.MAIN_THEME
import com.example.pictureoftheday.utils.getAppTheme
import com.example.pictureoftheday.utils.getAppThemeChoosen
import com.example.pictureoftheday.view.MainFragment

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        themeChoosen = getAppThemeChoosen(MAIN_THEME, this)
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
}