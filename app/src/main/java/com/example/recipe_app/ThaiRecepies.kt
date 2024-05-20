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

private const val TAG = "ThaiRecepies"

class ThaiRecepies : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var thaiRecipeContainer: LinearLayout
    private lateinit var searchView: SearchView
    private var thaiRecipes: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thai)

        db = Firebase.firestore
        thaiRecipeContainer = findViewById(R.id.ThairecipeContainer)
        searchView = findViewById(R.id.searchView)

        db.collection("Thai_recepies")
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    Log.w(TAG, "No items found.")
                    Toast.makeText(this, "No items found.", Toast.LENGTH_SHORT).show()
                    return@addOnSuccessListener
                }
                for (document in documents) {
                    val thaiRecipeName = document.id
                    thaiRecipes.add(thaiRecipeName)
                    addThaiRecipe(thaiRecipeName)
                }
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Error fetching names: $exception")
                Toast.makeText(this, "Failed to fetch names", Toast.LENGTH_SHORT).show()
            }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                filterThaiRecipes(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterThaiRecipes(newText)
                return false
            }
        })
    }

    private fun addThaiRecipe(thaiRecipeName: String) {
        val button = Button(this)
        button.text = thaiRecipeName
        button.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        button.setOnClickListener {
            val intent = Intent(this, BreakfastDetailsActivity::class.java)
            intent.putExtra("COLLECTION_NAME", "Thai_recepies")
            intent.putExtra("BREAKFAST_NAME", thaiRecipeName)
            startActivity(intent)
        }

        thaiRecipeContainer.addView(button)
    }

    private fun filterThaiRecipes(query: String?) {
        thaiRecipeContainer.removeAllViews()
        val filteredItems = thaiRecipes.filter { it.contains(query ?: "", ignoreCase = true) }
        filteredItems.forEach { addThaiRecipe(it) }
    }
}
