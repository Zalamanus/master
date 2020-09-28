package com.example.a12_lists.adapters

import android.view.View
import android.view.ViewGroup
import com.example.a12_lists.R
import com.example.a12_lists.model.Transport
import com.example.a12_lists.utils.inflate
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import kotlinx.android.synthetic.main.item_aicraft.*

class AircraftAdapterDelegate(private val onItemClicked: (position: Int) -> Unit) :
    AbsListItemAdapterDelegate<Transport.Aircraft, Transport, AircraftAdapterDelegate.AircraftHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup): AircraftHolder {
        return AircraftHolder(parent.inflate(R.layout.item_aicraft), onItemClicked)
    }

    override fun isForViewType(
        item: Transport,
        items: MutableList<Transport>,
        position: Int
    ): Boolean {
        return item is Transport.Aircraft
    }

    override fun onBindViewHolder(
        item: Transport.Aircraft,
        holder: AircraftHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }

    class AircraftHolder(containerView: View, onItemClicked: (position: Int) -> Unit) :
        TransportHolder(containerView, onItemClicked) {
        override fun bind(transport: Transport) {
            super.bind(transport)
            engineCountTV.text = (transport as Transport.Aircraft).engineCount.toString()
        }
    }
}