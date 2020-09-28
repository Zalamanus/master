package com.example.permissionsanddate.listadapter

import android.view.ViewGroup
import com.example.permissionsanddate.R
import com.example.permissionsanddate.model.MyLocation
import com.example.permissionsanddate.utils.inflate
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate

class MyLocationDelegate(private val onItemClicked: (position: Int) -> Unit) :
    AbsListItemAdapterDelegate<MyLocation, MyLocation, LocationHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup): LocationHolder {
        return LocationHolder(
            parent.inflate(
                R.layout.item_location
            ), onItemClicked
        )
    }

    override fun isForViewType(
        item: MyLocation,
        items: MutableList<MyLocation>,
        position: Int
    ): Boolean {
        return true
    }

    override fun onBindViewHolder(
        item: MyLocation,
        holder: LocationHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }

}



