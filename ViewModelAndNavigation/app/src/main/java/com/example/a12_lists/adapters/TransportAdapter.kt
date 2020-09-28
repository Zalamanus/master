package com.example.a12_lists.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.a12_lists.model.Transport
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class TransportAdapter(
    onItemShortTap: (position: Int) -> Unit,
    onItemLongTap: (position: Int) -> Boolean
) : AsyncListDifferDelegationAdapter<Transport>(TransportDiffUtilCallback()) {

    init {
        delegatesManager
            .addDelegate(CarAdapterDelegate(onItemShortTap, onItemLongTap))
            .addDelegate(AircraftAdapterDelegate(onItemShortTap, onItemLongTap))
            .addDelegate(ShipAdapterDelegate(onItemShortTap, onItemLongTap))

    }


    class TransportDiffUtilCallback : DiffUtil.ItemCallback<Transport>() {
        override fun areItemsTheSame(oldItem: Transport, newItem: Transport): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Transport, newItem: Transport): Boolean {
            return oldItem == newItem
        }

    }

}