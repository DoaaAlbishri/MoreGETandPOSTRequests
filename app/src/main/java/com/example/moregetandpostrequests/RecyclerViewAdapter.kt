package com.example.moregetandpostrequests

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_row.view.*

class RecyclerViewAdapter(private val words: ArrayList<String>) : RecyclerView.Adapter<RecyclerViewAdapter.ItemViewHolder>(){
    class ItemViewHolder(itemView : View) :RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
                LayoutInflater.from(parent.context).inflate(
                        R.layout.item_row
                        ,parent
                        ,false
                )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val word=words[position]

        holder.itemView.apply{

            tv.text=word
        }




    }

    override fun getItemCount(): Int =words.size

}