package com.example.recipe_app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.content.Intent

class Adapter(private val userList: ArrayList<RecipeCard>) :
    RecyclerView.Adapter<Adapter.MyViewHolder>() {

    public class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.name)
        val cuisine: TextView = itemView.findViewById(R.id.cuisine)
        val total_time_minutes: TextView = itemView.findViewById(R.id.total_time_minutes)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Adapter.MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.activity_list_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: Adapter.MyViewHolder, position: Int) {
        holder.name.text = userList[position].name
        holder.total_time_minutes.text = userList[position].total_time_minutes
        holder.cuisine.text = userList[position].cuisine

        // Inside the onBindViewHolder method, set click listener
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, recipe::class.java)
            intent.putExtra("recipeName", userList[position].name)
            intent.putExtra("recipeCuisine", userList[position].cuisine)
            intent.putExtra("recipeTotalTime", userList[position].total_time_minutes)

            // Add other data you want to pass
            intent.putExtra("servings", userList[position].servings)
            intent.putExtra("caloriesPerServing", userList[position].caloriesPerServing)
            intent.putExtra("description", userList[position].description)
            intent.putExtra("ingredients", userList[position].ingredients)
            intent.putExtra("instructions", userList[position].instructions)

            holder.itemView.context.startActivity(intent)
        }
    }


    override fun getItemCount(): Int {
        return userList.size
    }
}
