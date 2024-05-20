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

private const val TAG = "Brunch"

class Brunch : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var brunchContainer: LinearLayout
    private lateinit var searchView: SearchView
    private var brunchItems: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_brunch)

        db = Firebase.firestore
        brunchContainer = findViewById(R.id.brunchContainer)
        searchView = findViewById(R.id.searchView)

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
                    brunchItems.add(brunchName)
                    addBrunchItem(brunchName)
                }
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Error fetching brunch names: $exception")
                Toast.makeText(this, "Failed to fetch brunch names", Toast.LENGTH_SHORT).show()
            }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                filterBrunchItems(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterBrunchItems(newText)
                return false
            }
        })
    }

    private fun addBrunchItem(brunchName: String) {
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

    private fun filterBrunchItems(query: String?) {
        brunchContainer.removeAllViews()
        val filteredItems = brunchItems.filter { it.contains(query ?: "", ignoreCase = true) }
        filteredItems.forEach { addBrunchItem(it) }
    }
}
