package com.example.recipe_app

import android.content.Intent
import android.os.Bundle
import android.widget.HorizontalScrollView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.recipe_app.R.id.ImageButtoon25
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val logoutbtn = findViewById<Button>(R.id.logoutButton)
//        val searchBtn = findViewById<Button>(R.id.searchbtn)

        // Initialize the FirebaseAuth object
        firebaseAuth = FirebaseAuth.getInstance()

        logoutbtn.setOnClickListener {
            firebaseAuth.signOut()
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }
        val buttonBreakfast = findViewById<ImageButton>(R.id.ImageButtoon1)
        buttonBreakfast.setOnClickListener{
            val intent = Intent(this, Breakfast::class.java)
            startActivity(intent)
        }
        val buttonbrunch = findViewById<ImageButton>(R.id.ImageButtoon2)
        buttonbrunch.setOnClickListener{
            val intent = Intent(this,Brunch::class.java )
            startActivity(intent)
        }
        val buttonlunch = findViewById<ImageButton>(R.id.ImageButtoon3)
        buttonlunch.setOnClickListener{
            val intent = Intent(this,Lunch::class.java )
            startActivity(intent)
        }
        val buttondinner = findViewById<ImageButton>(R.id.ImageButtoon4)
        buttondinner.setOnClickListener{
            val intent = Intent(this,Dinner::class.java )
            startActivity(intent)
        }
        val buttonindian = findViewById<ImageButton>(R.id.ImageButtoon21)
        buttonindian.setOnClickListener{
            val intent = Intent(this,IndianRecepies::class.java )
            startActivity(intent)
        }
        val buttonitalian = findViewById<ImageButton>(R.id.ImageButtoon22)
        buttonitalian.setOnClickListener{
            val intent = Intent(this,ItalianRecepies::class.java )
            startActivity(intent)
        }
        val buttonjapnese = findViewById<ImageButton>(R.id.ImageButtoon23)
        buttonjapnese.setOnClickListener{
            val intent = Intent(this,JapneseRecepies::class.java )
            startActivity(intent)
        }
        val buttonmexican = findViewById<ImageButton>(R.id.ImageButtoon25)
        buttonmexican.setOnClickListener{
            val intent = Intent(this,MexicanRecepies::class.java )
            startActivity(intent)
        }
        val buttonthai = findViewById<ImageButton>(R.id.ImageButtoon24)
        buttonthai.setOnClickListener{
            val intent = Intent(this,ThaiRecepies::class.java )
            startActivity(intent)
        }


//        searchBtn.setOnClickListener {
//            // Open the SearchPage activity when the search button is clicked
//            val intent = Intent(this, SearchPage::class.java)
//            startActivity(intent)
//        }
  }

    override fun onStart() {
        super.onStart()
        val currentUser = firebaseAuth.currentUser


    }
}
