package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.model.CurrentWeatherResponse
import com.example.myapplication.model.HistoryItem

class AdapterHistory(private val weatherHistoryList: MutableList<CurrentWeatherResponse>) : RecyclerView.Adapter<AdapterHistory.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_history, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val HistoryItem = weatherHistoryList.get(position)

        // sets the image to the imageview from our itemHolder class
        //holder.imageView.setImageResource(HistoryItem.image)

        // sets the text to the textview from our itemHolder class
        //holder.txtDay.text = HistoryItem.text

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return weatherHistoryList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageview)
        val txtDay: TextView = itemView.findViewById(R.id.txtDay)
    }
}