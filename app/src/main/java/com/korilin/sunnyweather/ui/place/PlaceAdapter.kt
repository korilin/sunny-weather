package com.korilin.sunnyweather.ui.place

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.korilin.sunnyweather.R
import com.korilin.sunnyweather.logic.dao.Place

class PlaceAdapter(private val fragment: Fragment, private val placeList: List<Place>) : RecyclerView.Adapter<PlaceAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val placeName = view.findViewById(R.id.placeName) as TextView
        val placeAddress = view.findViewById(R.id.placeAddress) as TextView
    }

    override fun getItemCount(): Int = placeList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        placeList[position].also {
            holder.placeName.text = it.name
            holder.placeAddress.text = it.address
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.place_item, parent, false)
        return ViewHolder(view)
    }
}