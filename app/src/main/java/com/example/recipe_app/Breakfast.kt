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

private const val TAG = "Breakfast"

class Breakfast : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_breakfast)

        db = Firebase.firestore


        db.collection("breakfast")
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    Log.w(TAG, "No breakfast items found.")
                    Toast.makeText(this, "No breakfast items found.", Toast.LENGTH_SHORT).show()
                    return@addOnSuccessListener
                }
                for (document in documents) {
                    val breakfastName = document.id
                    addBreakfastItem(breakfastName)
                }
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Error fetching breakfast names: $exception")
                Toast.makeText(this, "Failed to fetch breakfast names", Toast.LENGTH_SHORT).show()
            }
    }

    private fun addBreakfastItem(breakfastName: String) {
        val breakfastContainer = findViewById<LinearLayout>(R.id.breakfastContainer)

        val button = Button(this)
        button.text = breakfastName
        button.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        button.setOnClickListener {
            val intent = Intent(this, BreakfastDetailsActivity::class.java)
            intent.putExtra("COLLECTION_NAME", "breakfast")
            intent.putExtra("BREAKFAST_NAME", breakfastName)
            startActivity(intent)
        }

        breakfastContainer.addView(button)
    }
}
