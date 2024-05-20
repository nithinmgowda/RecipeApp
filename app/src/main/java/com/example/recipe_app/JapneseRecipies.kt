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

private const val TAG = "JapneseRecepies"

class JapneseRecepies : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var japneseRecepiesContainer: LinearLayout
    private lateinit var searchView: SearchView
    private var japneseRecepiesItems: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_japnese)

        db = Firebase.firestore
        japneseRecepiesContainer = findViewById(R.id.japneserecepiesContainer)
        searchView = findViewById(R.id.searchView)

        // Fetch Japanese recipes from Firestore
        db.collection("Japnese_recepies")
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    Log.w(TAG, "No items found.")
                    Toast.makeText(this, "No items found.", Toast.LENGTH_SHORT).show()
                    return@addOnSuccessListener
                }
                for (document in documents) {
                    val japneseRecepies = document.id
                    japneseRecepiesItems.add(japneseRecepies)
                    addJapneseRecepiesItem(japneseRecepies)
                }
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Error fetching names: $exception")
                Toast.makeText(this, "Failed to fetch names", Toast.LENGTH_SHORT).show()
            }

        // Set up search functionality
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterJapneseRecepies(newText)
                return true
            }
        })
    }

    private fun addJapneseRecepiesItem(japneseRecepies: String) {
        val button = Button(this)
        button.text = japneseRecepies
        button.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        button.setOnClickListener {
            val intent = Intent(this, BreakfastDetailsActivity::class.java)
            intent.putExtra("COLLECTION_NAME", "Japnese_recepies")
            intent.putExtra("BREAKFAST_NAME", japneseRecepies)
            startActivity(intent)
        }

        japneseRecepiesContainer.addView(button)
    }

    private fun filterJapneseRecepies(query: String?) {
        japneseRecepiesContainer.removeAllViews()
        val filteredItems = japneseRecepiesItems.filter { it.contains(query ?: "", ignoreCase = true) }
        filteredItems.forEach { addJapneseRecepiesItem(it) }
    }
}
