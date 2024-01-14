package com.example.recipe_app

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class recipe : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)

        // Retrieve data from Intent extras
        val recipeName = intent.getStringExtra("recipeName")
        val recipeCuisine = intent.getStringExtra("recipeCuisine")
        val recipeTotalTime = intent.getStringExtra("recipeTotalTime")
        val servings = intent.getStringExtra("servings")
        val caloriesPerServing = intent.getStringExtra("caloriesPerServing")
        val description = intent.getStringExtra("description")
        val ingredients = intent.getStringExtra("ingredients")
        val instructions = intent.getStringExtra("instructions")

        // Find TextViews in your layout
        val recipeNameTextView: TextView = findViewById(R.id.recipeName)
        val cuisineTextView: TextView = findViewById(R.id.cuisine)
        val totalTimeTextView: TextView = findViewById(R.id.totalTime)
        val servingsTextView: TextView = findViewById(R.id.servings)
        val caloriesPerServingTextView: TextView = findViewById(R.id.caloriesPerServing)
        val descriptionTextView: TextView = findViewById(R.id.description)
        val ingredientsTextView: TextView = findViewById(R.id.ingredients)
        val instructionsTextView: TextView = findViewById(R.id.instructions)

        // Set the text with the retrieved data
        recipeNameTextView.text = recipeName
        cuisineTextView.text = "Cuisine: $recipeCuisine" // You can customize the text as needed
        totalTimeTextView.text = "Total Time: $recipeTotalTime minutes" // You can customize the text as needed
        servingsTextView.text = "Servings: $servings" // Customize as needed
        caloriesPerServingTextView.text = "Calories per Serving: $caloriesPerServing" // Customize as needed
        descriptionTextView.text = "Description: $description" // Customize as needed
        ingredientsTextView.text = "Ingredients: $ingredients" // Customize as needed
        instructionsTextView.text = "Instructions: $instructions" // Customize as needed
    }
}
