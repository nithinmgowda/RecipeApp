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

private const val TAG = "MexicanRecepies"

class MexicanRecepies : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var mexicanRecepiesContainer: LinearLayout
    private lateinit var searchView: SearchView
    private var mexicanRecepies: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mexican)

        db = Firebase.firestore
        mexicanRecepiesContainer = findViewById(R.id.mexicanrecepiesContainer)
        searchView = findViewById(R.id.searchView)

        db.collection("Mexician_recepies")
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    Log.w(TAG, "No items found.")
                    Toast.makeText(this, "No items found.", Toast.LENGTH_SHORT).show()
                    return@addOnSuccessListener
                }
                for (document in documents) {
                    val mexicanRecepieName = document.id
                    mexicanRecepies.add(mexicanRecepieName)
                    addMexicanRecepie(mexicanRecepieName)
                }
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Error fetching names: $exception")
                Toast.makeText(this, "Failed to fetch names", Toast.LENGTH_SHORT).show()
            }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                filterMexicanRecepies(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterMexicanRecepies(newText)
                return false
            }
        })
    }

    private fun addMexicanRecepie(mexicanRecepieName: String) {
        val button = Button(this)
        button.text = mexicanRecepieName
        button.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        button.setOnClickListener {
            val intent = Intent(this, BreakfastDetailsActivity::class.java)
            intent.putExtra("COLLECTION_NAME", "Mexician_recepies")
            intent.putExtra("BREAKFAST_NAME", mexicanRecepieName)
            startActivity(intent)
        }

        mexicanRecepiesContainer.addView(button)
    }

    private fun filterMexicanRecepies(query: String?) {
        mexicanRecepiesContainer.removeAllViews()
        val filteredItems = mexicanRecepies.filter { it.contains(query ?: "", ignoreCase = true) }
        filteredItems.forEach { addMexicanRecepie(it) }
    }
}
