package com.example.recipe_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class intro_page : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro_page)
        val lgnbtn = findViewById<Button>(R.id.intrologin);
        val signupbtn = findViewById<Button>(R.id.introsignin);

        lgnbtn.setOnClickListener {
            val intent = Intent(this,Login::class.java)
            startActivity(intent)
        }
        signupbtn.setOnClickListener {
            val intent = Intent(this,Signup::class.java)
            startActivity(intent)
        }

    }
}