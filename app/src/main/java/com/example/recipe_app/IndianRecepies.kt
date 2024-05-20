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

private const val TAG = "IndianRecepies"

class IndianRecepies : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var indianrecepiesContainer: LinearLayout
    private lateinit var searchView: SearchView
    private var indianrecepiesItems: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_indianrecepies)

        db = Firebase.firestore
        indianrecepiesContainer = findViewById(R.id.indianrecepiesContainer)
        searchView = findViewById(R.id.searchView)

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
                    indianrecepiesItems.add(indianrecepies)
                    addIndianRecepiesItem(indianrecepies)
                }
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Error fetching names: $exception")
                Toast.makeText(this, "Failed to fetch names", Toast.LENGTH_SHORT).show()
            }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                filterIndianRecepiesItems(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterIndianRecepiesItems(newText)
                return false
            }
        })
    }

    private fun addIndianRecepiesItem(indianrecepies: String) {
        val button = Button(this)
        button.text = indianrecepies
        button.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        button.setOnClickListener {
            val intent = Intent(this, BreakfastDetailsActivity::class.java)
            intent.putExtra("COLLECTION_NAME", "Indian_recepies")
            intent.putExtra("BREAKFAST_NAME", indianrecepies)
            startActivity(intent)
        }

        indianrecepiesContainer.addView(button)
    }

    private fun filterIndianRecepiesItems(query: String?) {
        indianrecepiesContainer.removeAllViews()
        val filteredItems = indianrecepiesItems.filter { it.contains(query ?: "", ignoreCase = true) }
        filteredItems.forEach { addIndianRecepiesItem(it) }
    }
}
