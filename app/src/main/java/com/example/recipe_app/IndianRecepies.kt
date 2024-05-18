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

private const val TAG = "IndianRecepies"

class IndianRecepies : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_indianrecepies)

        db = Firebase.firestore


        db.collection("Indian_recepies")
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    Log.w(TAG, "No items found.")
                    Toast.makeText(this, "No items found.", Toast.LENGTH_SHORT).show()
                    return@addOnSuccessListener
                }
                for (document in documents) {
                    val indianrecepies = document.id
                    addBreakfastItem(indianrecepies)
                }
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Error fetching names: $exception")
                Toast.makeText(this, "Failed to fetch names", Toast.LENGTH_SHORT).show()
            }
    }

    private fun addBreakfastItem(indianrecipiesname: String) {
        val brunchContainer = findViewById<LinearLayout>(R.id.indianrecepiesContainer)

        val button = Button(this)
        button.text = indianrecipiesname
        button.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        button.setOnClickListener {
            val intent = Intent(this, BreakfastDetailsActivity::class.java)
            intent.putExtra("COLLECTION_NAME", "Indian_recepies")
            intent.putExtra("BREAKFAST_NAME", indianrecipiesname)
            startActivity(intent)
        }

        brunchContainer.addView(button)
    }
}


