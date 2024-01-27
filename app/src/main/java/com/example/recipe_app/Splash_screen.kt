package com.example.recipe_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.google.firebase.auth.FirebaseAuth

class Splash_screen : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        // Initialize FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()

        // Check if the user is already logged in
        val isLoggedIn = checkIfUserIsLoggedIn()

        // Determine the destination activity based on login status
        val destinationActivity = if (isLoggedIn) {
            MainActivity::class.java
        } else {
            intro_page::class.java
        }

        // Delay for 3 seconds before navigating to the destination activity
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, destinationActivity)
            startActivity(intent)
            finish()
        }, 3000)
    }

    // Actual login check logic using FirebaseAuth
    private fun checkIfUserIsLoggedIn(): Boolean {
        val currentUser = firebaseAuth.currentUser
        return currentUser != null
    }
}
