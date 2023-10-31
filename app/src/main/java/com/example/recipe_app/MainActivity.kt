package com.example.recipe_app

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth // Import FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth // Initialize the FirebaseAuth object

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val logoutbtn=findViewById<Button>(R.id.Logoutbtn)
        logoutbtn.setOnClickListener {
            firebaseAuth.signOut() // Sign out the user
            val intent = Intent(this, Login::class.java) // Navigate to the login screen
            startActivity(intent)
            finish() // Close the current activity
        }
        // Initialize the FirebaseAuth object
        firebaseAuth = FirebaseAuth.getInstance()
    }

    override fun onStart() {
        super.onStart()
        val currentUser = firebaseAuth.currentUser

        if (currentUser != null) {
            // The user is already logged in, you can proceed with the app.
        } else {
            // The user is not logged in, you can navigate to the login screen.
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }
    }
}
