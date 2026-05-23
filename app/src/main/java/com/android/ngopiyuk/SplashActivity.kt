package com.android.ngopiyuk

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private val SPLASH_DELAY = 2800L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val ivLogo     = findViewById<ImageView>(R.id.iv_logo)
        val tvLabel    = findViewById<TextView>(R.id.tv_logo_label)
        val tvAppName  = findViewById<TextView>(R.id.tv_app_name)
        val tvTagline  = findViewById<TextView>(R.id.tv_tagline)
        val llDots     = findViewById<LinearLayout>(R.id.ll_dots)

        // --- Phase 1: Logo fades + scales in (0ms) ---
        ivLogo.scaleX = 0.6f
        ivLogo.scaleY = 0.6f
        ivLogo.animate()
            .alpha(1f)
            .scaleX(1f)
            .scaleY(1f)
            .setDuration(600)
            .setStartDelay(100)
            .setInterpolator(DecelerateInterpolator(1.5f))
            .start()

        // --- Phase 2: Small logo label fades in (500ms) ---
        tvLabel.animate()
            .alpha(1f)
            .translationY(0f)
            .setDuration(450)
            .setStartDelay(500)
            .setInterpolator(DecelerateInterpolator())
            .start()
        tvLabel.translationY = 12f

        // --- Phase 3: App Name slides + fades in (750ms) ---
        tvAppName.translationY = 24f
        tvAppName.animate()
            .alpha(1f)
            .translationY(0f)
            .setDuration(500)
            .setStartDelay(750)
            .setInterpolator(DecelerateInterpolator())
            .start()

        // --- Phase 4: Tagline + dots fade in (1100ms) ---
        tvTagline.animate()
            .alpha(1f)
            .setDuration(500)
            .setStartDelay(1100)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .start()

        llDots.animate()
            .alpha(1f)
            .setDuration(500)
            .setStartDelay(1300)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .start()

        // --- Navigate to MainActivity after delay ---
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            // Smooth cross-fade transition
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }, SPLASH_DELAY)
    }
}
