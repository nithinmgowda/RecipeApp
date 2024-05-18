package com.example.recipe_app

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.DocumentSnapshot
import android.util.Log

private const val TAG = "BreakfastDetails"

class BreakfastDetailsActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.acitvity_breakfast_details)

        val breakfastName = intent.getStringExtra("BREAKFAST_NAME") ?: return
        val collectionName = intent.getStringExtra("COLLECTION_NAME") ?: "breakfast"  // Default to "breakfast" if not provided

        val breakfastNameTextView = findViewById<TextView>(R.id.breakfastName)
        val breakfastTypeTextView = findViewById<TextView>(R.id.breakfastType)
        val ingredientsTextView = findViewById<TextView>(R.id.ingredients)
        val directionsTextView = findViewById<TextView>(R.id.directions)
        val nutritionTextView = findViewById<TextView>(R.id.nutrition)

        val btnIngredients = findViewById<Button>(R.id.btnIngredients)
        val btnDirections = findViewById<Button>(R.id.btnDirections)
        val btnNutrition = findViewById<Button>(R.id.btnNutrition)

        breakfastNameTextView.text = breakfastName

        db.collection(collectionName).document(breakfastName)
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                    displayData(document, breakfastTypeTextView, ingredientsTextView, directionsTextView, nutritionTextView)
                } else {
                    Toast.makeText(this, "No such document", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Error fetching document: $exception", Toast.LENGTH_SHORT).show()
            }

        // Set default view to Ingredients
        showIngredients(ingredientsTextView, directionsTextView, nutritionTextView)

        btnIngredients.setOnClickListener {
            showIngredients(ingredientsTextView, directionsTextView, nutritionTextView)
        }

        btnDirections.setOnClickListener {
            showDirections(ingredientsTextView, directionsTextView, nutritionTextView)
        }

        btnNutrition.setOnClickListener {
            showNutrition(ingredientsTextView, directionsTextView, nutritionTextView)
        }
    }

    private fun displayData(document: DocumentSnapshot, typeTextView: TextView, ingredientsTextView: TextView, directionsTextView: TextView, nutritionTextView: TextView) {
        typeTextView.text = document.getString("type") ?: "N/A"
        ingredientsTextView.text = (document.get("ingredients") as? List<*>)?.joinToString("\n") ?: "N/A"
        directionsTextView.text = (document.get("directions") as? List<*>)?.joinToString("\n") ?: "N/A"

        val nutritionMap = document.get("nutritional_value") as? Map<*, *>
        nutritionTextView.text = nutritionMap?.entries?.joinToString("\n") { "${it.key}: ${it.value}" } ?: "N/A"
    }

    private fun showIngredients(ingredients: View, directions: View, nutrition: View) {
        ingredients.visibility = View.VISIBLE
        directions.visibility = View.GONE
        nutrition.visibility = View.GONE
    }

    private fun showDirections(ingredients: View, directions: View, nutrition: View) {
        ingredients.visibility = View.GONE
        directions.visibility = View.VISIBLE
        nutrition.visibility = View.GONE
    }

    private fun showNutrition(ingredients: View, directions: View, nutrition: View) {
        ingredients.visibility = View.GONE
        directions.visibility = View.GONE
        nutrition.visibility = View.VISIBLE
    }
}
