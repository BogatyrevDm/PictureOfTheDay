package com.example.pictureoftheday.utils

import android.view.ViewGroup
import androidx.transition.ChangeBounds
import androidx.transition.ChangeImageTransform
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet

fun BeginDelayedTransition(view: ViewGroup) {
    TransitionManager.beginDelayedTransition(
        view,
        TransitionSet()
            .addTransition(ChangeBounds())
            .addTransition(ChangeImageTransform())
    )
}