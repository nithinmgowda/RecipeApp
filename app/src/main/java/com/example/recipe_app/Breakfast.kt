package com.example.recipe_app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

private const val TAG = "Breakfast"

class Breakfast : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var breakfastContainer: LinearLayout
    private lateinit var searchView: SearchView
    private var breakfastItems: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_breakfast)

        db = Firebase.firestore
        breakfastContainer = findViewById(R.id.breakfastContainer)
        searchView = findViewById(R.id.searchView)

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
                    breakfastItems.add(breakfastName)
                    addBreakfastItem(breakfastName)
                }
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Error fetching breakfast names: $exception")
                Toast.makeText(this, "Failed to fetch breakfast names", Toast.LENGTH_SHORT).show()
            }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                filterBreakfastItems(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterBreakfastItems(newText)
                return false
            }
        })
    }

    private fun addBreakfastItem(breakfastName: String) {
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

    private fun filterBreakfastItems(query: String?) {
        breakfastContainer.removeAllViews()
        val filteredItems = breakfastItems.filter { it.contains(query ?: "", ignoreCase = true) }
        filteredItems.forEach { addBreakfastItem(it) }
    }
}
