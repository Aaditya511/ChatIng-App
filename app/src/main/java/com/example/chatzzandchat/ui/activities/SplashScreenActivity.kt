package com.example.chatzzandchat.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.example.chatzzandchat.R
import com.google.firebase.auth.FirebaseAuth

class SplashScreenActivity : AppCompatActivity() {
    val auth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        if (auth.currentUser != null) {
            startActivity(Intent(this, UserListActivity::class.java))
            finish()
        } else {
            hideStatusBar()
            splashScreenTimer()
        }

    }

    fun splashScreenTimer() {
        Handler().postDelayed(Runnable {
            startActivity(Intent(this, GetUserMobileNumActivity::class.java))
            finish()
        }, 1000)
    }

    fun hideStatusBar() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN

        )
    }
}