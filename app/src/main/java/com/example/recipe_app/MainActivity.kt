package com.example.recipe_app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val logoutbtn = findViewById<Button>(R.id.Logoutbtn)
        val searchBtn = findViewById<Button>(R.id.searchbtn)

        // Initialize the FirebaseAuth object
        firebaseAuth = FirebaseAuth.getInstance()

        logoutbtn.setOnClickListener {
            firebaseAuth.signOut()
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }

        searchBtn.setOnClickListener {
            // Open the SearchPage activity when the search button is clicked
            val intent = Intent(this, SearchPage::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = firebaseAuth.currentUser


    }
}
