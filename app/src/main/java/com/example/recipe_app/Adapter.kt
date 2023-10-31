package com.example.recipe_app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Adapter(private val userList:ArrayList<RecipeCard>) :RecyclerView.Adapter<Adapter.MyViewHolder>(){


    public class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val name:TextView =itemView.findViewById(R.id.name)
        val cuisine :TextView =itemView.findViewById(R.id.cuisine )
        val total_time_minutes:TextView =itemView.findViewById(R.id.total_time_minutes)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Adapter.MyViewHolder {
       val itemView=LayoutInflater.from(parent.context).inflate(R.layout.activity_list_item,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: Adapter.MyViewHolder, position: Int) {
      holder.name.text=userList[position].name
        holder.total_time_minutes.text=userList[position].total_time_minutes
        holder.cuisine.text=userList[position].cuisine
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}
