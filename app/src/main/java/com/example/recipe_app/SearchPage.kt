package com.example.recipe_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class SearchPage : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var userList: ArrayList<RecipeCard>
    private var db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_page)
        recyclerView=findViewById(R.id.RecyclerView)
        recyclerView.layoutManager=LinearLayoutManager(this)
        userList= arrayListOf()
        db= FirebaseFirestore.getInstance()
        db.collection("RecipeData").get().addOnSuccessListener {
           if(!it.isEmpty){
               for(data in it.documents){
                  val user:RecipeCard?=data.toObject(RecipeCard::class.java)
                  if (user!=null){
                      userList.add(user)
                  }
               }
               recyclerView.adapter=Adapter(userList)
           }
        }.addOnFailureListener{
            Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
        }
    }
}