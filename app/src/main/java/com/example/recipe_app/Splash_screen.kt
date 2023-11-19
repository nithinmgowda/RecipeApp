package com.example.recipe_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class Splash_screen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
      Handler(Looper.getMainLooper()).postDelayed({
          val toHome = Intent(this,MainActivity::class.java)
          startActivity(toHome)
          finish()
      },3000)

    }
}