package com.example.mytask

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class SplashScreen : AppCompatActivity() {
    var handler = Handler()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val r = Runnable {
            val intent = Intent(this@SplashScreen, DashBoard::class.java)
            startActivity(intent)
        }
        handler.postDelayed(r, 2000)
    }
}