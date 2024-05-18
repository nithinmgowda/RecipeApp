package com.example.recipe_app
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot

class BreakfastPage: AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var userList: ArrayList<RecipeCard>
    private lateinit var adapter: Adapter
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_page)

        recyclerView = findViewById(R.id.RecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        userList = arrayListOf()
        adapter = Adapter(userList)
        recyclerView.adapter = adapter

        // Fetch data from Firestore
        fetchDataFromFirestore()
    }

    private fun fetchDataFromFirestore() {
        db.collection("recipes").get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val recipeCard = document.toObject(RecipeCard::class.java)
                    userList.add(recipeCard)
                }
                // Notify the adapter that data has changed
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Error getting data from Firestore: $exception", Toast.LENGTH_SHORT).show()
            }
    }
}
