package com.example.a12_lists.fragments

import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.a12_lists.adapters.TransportAdapter
import kotlinx.android.synthetic.main.fragment_transport_list.*

class TransportStaggeredGridFragment: TransportListFragment() {
    override fun initList() {
        configTransportAdapter()
        with(transportList) {
            adapter = transportAdapter
            layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
        }
    }

}