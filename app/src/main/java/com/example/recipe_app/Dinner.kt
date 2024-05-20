package com.example.recipe_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

private const val TAG = "Dinner"

class Dinner : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var dinnerContainer: LinearLayout
    private lateinit var searchView: SearchView
    private var dinnerItems: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dinner)

        db = Firebase.firestore
        dinnerContainer = findViewById(R.id.dinnerContainer)
        searchView = findViewById(R.id.searchView)

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
                    dinnerItems.add(dinnerName)
                    addDinnerItem(dinnerName)
                }
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Error fetching dinner names: $exception")
                Toast.makeText(this, "Failed to fetch dinner names", Toast.LENGTH_SHORT).show()
            }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                filterDinnerItems(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterDinnerItems(newText)
                return false
            }
        })
    }

    private fun addDinnerItem(dinnerName: String) {
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

        dinnerContainer.addView(button)
    }

    private fun filterDinnerItems(query: String?) {
        dinnerContainer.removeAllViews()
        val filteredItems = dinnerItems.filter { it.contains(query ?: "", ignoreCase = true) }
        filteredItems.forEach { addDinnerItem(it) }
    }
}
