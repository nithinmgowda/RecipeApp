package com.example.recipe_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

private const val TAG = "Dinner"

class Dinner : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dinner)

        db = Firebase.firestore


        db.collection("dinner")
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    Log.w(TAG, "No dinner items found.")
                    Toast.makeText(this, "No dinner items found.", Toast.LENGTH_SHORT).show()
                    return@addOnSuccessListener
                }
                for (document in documents) {
                    val dinnerName = document.id
                    addBreakfastItem(dinnerName)
                }
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Error fetching dinner names: $exception")
                Toast.makeText(this, "Failed to fetch dinner names", Toast.LENGTH_SHORT).show()
            }
    }

    private fun addBreakfastItem(dinnerName: String) {
        val brunchContainer = findViewById<LinearLayout>(R.id.dinnerContainer)

        val button = Button(this)
        button.text = dinnerName
        button.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        button.setOnClickListener {
            val intent = Intent(this, BreakfastDetailsActivity::class.java)
            intent.putExtra("COLLECTION_NAME", "dinner")
            intent.putExtra("BREAKFAST_NAME", dinnerName)
            startActivity(intent)
        }

        brunchContainer.addView(button)
    }
}


