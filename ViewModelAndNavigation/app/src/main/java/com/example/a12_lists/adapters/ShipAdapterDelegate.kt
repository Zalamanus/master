package com.example.a12_lists.adapters

import android.view.View
import android.view.ViewGroup
import com.example.a12_lists.R
import com.example.a12_lists.model.Transport
import com.example.a12_lists.utils.inflate
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import kotlinx.android.synthetic.main.item_ship.*

class ShipAdapterDelegate(private val onItemShortTap: (position: Int) -> Unit,private val onItemLongTap: (position: Int) -> Boolean) :
    AbsListItemAdapterDelegate<Transport.Ship, Transport, ShipAdapterDelegate.ShipHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup): ShipHolder {
        return ShipHolder(parent.inflate(R.layout.item_ship), onItemShortTap, onItemLongTap)
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

    class ShipHolder(
        containerView: View,
        onItemShortTap: (position: Int) -> Unit,
        onItemLongTap: (position: Int) -> Boolean
    ) : TransportHolder(containerView, onItemShortTap, onItemLongTap) {
        override fun bind(transport: Transport) {
            super.bind(transport)
            displacementTV.text = containerView.context
                .getString(R.string.displacement_placeholder,(transport as Transport.Ship).displacement)
        }
    }
}