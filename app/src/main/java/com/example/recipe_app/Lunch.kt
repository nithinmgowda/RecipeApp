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

private const val TAG = "Lunch"

class Lunch : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var lunchContainer: LinearLayout
    private lateinit var searchView: SearchView
    private var lunchItems: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lunch)

        db = Firebase.firestore
        lunchContainer = findViewById(R.id.lunchContainer)
        searchView = findViewById(R.id.searchView)

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
                    lunchItems.add(lunchName)
                    addLunchItem(lunchName)
                }
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Error fetching lunch names: $exception")
                Toast.makeText(this, "Failed to fetch lunch names", Toast.LENGTH_SHORT).show()
            }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterLunchItems(newText)
                return true
            }
        })
    }

    private fun addLunchItem(lunchName: String) {
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

        lunchContainer.addView(button)
    }

    private fun filterLunchItems(query: String?) {
        lunchContainer.removeAllViews()
        val filteredItems = lunchItems.filter { it.contains(query ?: "", ignoreCase = true) }
        filteredItems.forEach { addLunchItem(it) }
    }
}
