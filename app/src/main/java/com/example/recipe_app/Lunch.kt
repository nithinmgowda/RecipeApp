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

private const val TAG = "Lunch"

class Lunch : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lunch)

        db = Firebase.firestore


        db.collection("lunch")
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    Log.w(TAG, "No lunch items found.")
                    Toast.makeText(this, "No lunch items found.", Toast.LENGTH_SHORT).show()
                    return@addOnSuccessListener
                }
                for (document in documents) {
                    val lunchName = document.id
                    addBreakfastItem(lunchName)
                }
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Error fetching lunch names: $exception")
                Toast.makeText(this, "Failed to fetch lunch names", Toast.LENGTH_SHORT).show()
            }
    }

    private fun addBreakfastItem(lunchName: String) {
        val brunchContainer = findViewById<LinearLayout>(R.id.lunchContainer)

        val button = Button(this)
        button.text = lunchName
        button.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        button.setOnClickListener {
            val intent = Intent(this, BreakfastDetailsActivity::class.java)
            intent.putExtra("COLLECTION_NAME", "lunch")
            intent.putExtra("BREAKFAST_NAME", lunchName)
            startActivity(intent)
        }

        brunchContainer.addView(button)
    }
}


