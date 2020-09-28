package com.example.a12_lists.adapters

import android.view.View
import android.view.ViewGroup
import com.example.a12_lists.R
import com.example.a12_lists.model.Transport
import com.example.a12_lists.utils.inflate
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import kotlinx.android.synthetic.main.item_car.*

class CarAdapterDelegate(private val onItemShortTap: (position: Int) -> Unit, private val onItemLongTap: (position: Int) -> Boolean) :
    AbsListItemAdapterDelegate<Transport.Car, Transport, CarAdapterDelegate.CarHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup): CarHolder {
        return CarHolder(parent.inflate(R.layout.item_car), onItemShortTap, onItemLongTap)
    }

    override fun isForViewType(
        item: Transport,
        items: MutableList<Transport>,
        position: Int
    ): Boolean {
        return item is Transport.Car
    }

    override fun onBindViewHolder(
        item: Transport.Car,
        holder: CarHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }

    class CarHolder(
        containerView: View,
        onItemShortTap: (position: Int) -> Unit,
        onItemLongTap: (position: Int) -> Boolean
    ) : TransportHolder(containerView, onItemShortTap, onItemLongTap) {
        override fun bind(transport: Transport) {
            super.bind(transport)
            doorCountTV.text = containerView.context
                .getString(R.string.door_count_placeholder,(transport as Transport.Car).doorCount)
        }
    }
}