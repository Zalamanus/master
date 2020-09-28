package com.example.permissionsanddate.listadapter

import androidx.recyclerview.widget.DiffUtil
import com.example.permissionsanddate.model.MyLocation
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class LocationAdapter(
    onItemClicked: (position: Int) -> Unit
) : AsyncListDifferDelegationAdapter<MyLocation>(TransportDiffUtilCallback()) {

    init {
        delegatesManager
            .addDelegate(
                MyLocationDelegate(
                    onItemClicked
                )
            )

    }


    class TransportDiffUtilCallback : DiffUtil.ItemCallback<MyLocation>() {
        override fun areItemsTheSame(oldItem: MyLocation, newItem: MyLocation): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MyLocation, newItem: MyLocation): Boolean {
            return oldItem == newItem
        }

    }


}