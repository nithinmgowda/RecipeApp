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
        val breakfastbtn = findViewById<Button>(R.id.bf_btn)
        val lunch = findViewById<Button>(R.id.Lunch_btn)
        val dinner= findViewById<Button>(R.id.Dinner_btn)
        val brunch = findViewById<Button>(R.id.Brunch_btn)

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
        breakfastbtn.setOnClickListener {
            // Open the SearchPage activity when the search button is clicked
            val intent = Intent(this, BreakfastPage::class.java)
            startActivity(intent)
        }
        lunch.setOnClickListener {
            // Open the SearchPage activity when the search button is clicked
            val intent = Intent(this, LunchPage::class.java)
            startActivity(intent)
        }
        dinner.setOnClickListener {
            // Open the SearchPage activity when the search button is clicked
            val intent = Intent(this, DinnerPage::class.java)
            startActivity(intent)
        }
        brunch.setOnClickListener {
            // Open the SearchPage activity when the search button is clicked
            val intent = Intent(this, BrunchPage::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = firebaseAuth.currentUser


    }
}
