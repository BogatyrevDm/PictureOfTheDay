package com.example.pictureoftheday.utils

import android.view.Gravity
import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.toast(string: String?) {
    Toast.makeText(context, string, Toast.LENGTH_LONG).apply {
        setGravity(Gravity.BOTTOM, 0, 250)
        show()
    }
}
