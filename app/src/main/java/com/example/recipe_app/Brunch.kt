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

private const val TAG = "Brunch"

class Brunch : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_brunch)

        db = Firebase.firestore


        db.collection("brunch")
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    Log.w(TAG, "No brunch items found.")
                    Toast.makeText(this, "No brunch items found.", Toast.LENGTH_SHORT).show()
                    return@addOnSuccessListener
                }
                for (document in documents) {
                    val brunchName = document.id
                    addBreakfastItem(brunchName)
                }
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Error fetching brunch names: $exception")
                Toast.makeText(this, "Failed to fetch brunch names", Toast.LENGTH_SHORT).show()
            }
    }

    private fun addBreakfastItem(brunchName: String) {
        val brunchContainer = findViewById<LinearLayout>(R.id.brunchContainer)

        val button = Button(this)
        button.text = brunchName
        button.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        button.setOnClickListener {
            val intent = Intent(this, BreakfastDetailsActivity::class.java)
            intent.putExtra("COLLECTION_NAME", "brunch")
            intent.putExtra("BREAKFAST_NAME", brunchName)
            startActivity(intent)
        }

        brunchContainer.addView(button)
    }
}


