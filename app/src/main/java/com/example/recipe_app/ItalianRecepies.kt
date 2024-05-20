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

private const val TAG = "ItalianRecepies"

class ItalianRecepies : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var italianrecepiesContainer: LinearLayout
    private lateinit var searchView: SearchView
    private var italianrecepiesItems: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_italian)

        db = Firebase.firestore
        italianrecepiesContainer = findViewById(R.id.italianrecepiesContainer)
        searchView = findViewById(R.id.searchView)

        db.collection("Italian_recepies")
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    Log.w(TAG, "No items found.")
                    Toast.makeText(this, "No items found.", Toast.LENGTH_SHORT).show()
                    return@addOnSuccessListener
                }
                for (document in documents) {
                    val italianrecepies = document.id
                    italianrecepiesItems.add(italianrecepies)
                    addItalianRecepiesItem(italianrecepies)
                }
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Error fetching names: $exception")
                Toast.makeText(this, "Failed to fetch names", Toast.LENGTH_SHORT).show()
            }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                filterItalianRecepiesItems(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterItalianRecepiesItems(newText)
                return false
            }
        })
    }

    private fun addItalianRecepiesItem(italianrecepies: String) {
        val button = Button(this)
        button.text = italianrecepies
        button.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        button.setOnClickListener {
            val intent = Intent(this, BreakfastDetailsActivity::class.java)
            intent.putExtra("COLLECTION_NAME", "Italian_recepies")
            intent.putExtra("BREAKFAST_NAME", italianrecepies)
            startActivity(intent)
        }

        italianrecepiesContainer.addView(button)
    }

    private fun filterItalianRecepiesItems(query: String?) {
        italianrecepiesContainer.removeAllViews()
        val filteredItems = italianrecepiesItems.filter { it.contains(query ?: "", ignoreCase = true) }
        filteredItems.forEach { addItalianRecepiesItem(it) }
    }
}
