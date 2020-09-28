package com.example.a12_lists.fragments

import androidx.recyclerview.widget.GridLayoutManager
import com.example.a12_lists.utils.ItemDecoration
import kotlinx.android.synthetic.main.fragment_transport_list.*

class TransportGridFragment : TransportListFragment() {
    override fun initList() {
        configTransportAdapter()
        with(transportList) {
            adapter = transportAdapter
            addItemDecoration(ItemDecoration(context))
            layoutManager = GridLayoutManager(this.context, 2)
            setHasFixedSize(false)
        }
    }
}