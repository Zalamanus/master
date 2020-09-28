package com.example.a12_lists.adapters

import android.view.View
import android.view.ViewGroup
import com.example.a12_lists.R
import com.example.a12_lists.model.Transport
import com.example.a12_lists.utils.inflate
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import kotlinx.android.synthetic.main.item_ship.*

class ShipAdapterDelegate(private val onItemClicked: (position: Int) -> Unit) :
    AbsListItemAdapterDelegate<Transport.Ship, Transport, ShipAdapterDelegate.ShipHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup): ShipHolder {
        return ShipHolder(parent.inflate(R.layout.item_ship), onItemClicked)
    }

    override fun isForViewType(
        item: Transport,
        items: MutableList<Transport>,
        position: Int
    ): Boolean {
        return item is Transport.Ship
    }

    override fun onBindViewHolder(
        item: Transport.Ship,
        holder: ShipHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }

    class ShipHolder(containerView: View, onItemClicked: (position: Int) -> Unit) :
        TransportHolder(containerView, onItemClicked) {
        override fun bind(transport: Transport) {
            super.bind(transport)
            displacementTV.text = (transport as Transport.Ship).displacement.toString()
        }
    }
}