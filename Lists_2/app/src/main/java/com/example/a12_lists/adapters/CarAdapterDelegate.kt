package com.example.a12_lists.adapters

import android.view.View
import android.view.ViewGroup
import com.example.a12_lists.R
import com.example.a12_lists.model.Transport
import com.example.a12_lists.utils.inflate
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import kotlinx.android.synthetic.main.item_car.*

class CarAdapterDelegate(private val onItemClicked: (position: Int) -> Unit) :
    AbsListItemAdapterDelegate<Transport.Car, Transport, CarAdapterDelegate.CarHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup): CarHolder {
        return CarHolder(parent.inflate(R.layout.item_car), onItemClicked)
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

    class CarHolder(containerView: View, onItemClicked: (position: Int) -> Unit) :
        TransportHolder(containerView, onItemClicked) {
        override fun bind(transport: Transport) {
            super.bind(transport)
            doorCountTV?.text = (transport as Transport.Car).doorCount.toString()
        }
    }
}