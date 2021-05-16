package com.example.pictureoftheday

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import com.example.pictureoftheday.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    var _binding: ActivitySplashBinding? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.imageViewSplash.animate().scaleY(-1f).scaleX(-1f)
            .setInterpolator(AccelerateDecelerateInterpolator()).setDuration(2750).setListener(
                object : Animator.AnimatorListener {
                    override fun onAnimationStart(animation: Animator?) {
                    }

                    override fun onAnimationEnd(animation: Animator?) {
                        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                        finish()
                    }

                    override fun onAnimationCancel(animation: Animator?) {
                    }

                    override fun onAnimationRepeat(animation: Animator?) {
                    }
                }
            )
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}