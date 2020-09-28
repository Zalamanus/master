package com.example.a12_lists.fragments

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a12_lists.adapters.TransportAdapter
import kotlinx.android.synthetic.main.fragment_transport_list.*

class TransportHorizontalListFragment: TransportListFragment() {
    override fun initList() {
        configTransportAdapter()
        with(transportList) {
            adapter = transportAdapter
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL,false)
            setHasFixedSize(true)
        }
    }
}