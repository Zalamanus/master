package com.example.a12_lists.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a12_lists.*
import com.example.a12_lists.adapters.TransportAdapter
import com.example.a12_lists.model.TransportListViewModel
import com.example.a12_lists.model.TransportType
import com.example.a12_lists.utils.AutoClearedValue
import com.example.a12_lists.utils.EndlessRecyclerViewScrollListener
import com.example.a12_lists.utils.showToast
import jp.wasabeef.recyclerview.animators.SlideInDownAnimator
import kotlinx.android.synthetic.main.fragment_transport_list.*


open class TransportListFragment : Fragment(R.layout.fragment_transport_list) {

    private var transportAdapter by AutoClearedValue<TransportAdapter>()
    private val transportListViewModel: TransportListViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initList()
        addFab.setOnClickListener { addTransport() }
        observeViewModelState()
    }

    private fun showDetails(position: Int) {
        val transport = transportAdapter.items[position]
        val action = TransportListFragmentDirections
            .actionTransportListFragmentToDetailsFragment(transport)
        findNavController().navigate(action)

    }

    private fun delTransport(position: Int): Boolean {
        transportListViewModel.delTransport(position)
        if (transportAdapter.items.isEmpty()) emptyListTV.visibility = View.VISIBLE
        return true
    }

    private fun addTransport() {
        val newFragment =
            AddTransportDialogFragment()
        newFragment.show(childFragmentManager, "addTransport_dialog")
    }

    fun addTransport(
        transportName: String,
        transportType: TransportType,
        maxSpeed: Int,
        typeRelatedParam: Int
    ) {
        transportListViewModel.addTransport(
            transportName,
            transportType,
            maxSpeed,
            typeRelatedParam
        )
        emptyListTV.visibility = View.GONE

    }


    open fun initList() {
        configTransportAdapter()
        val divider = DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL)
        with(transportList) {
            adapter = transportAdapter
            addItemDecoration(divider)
            itemAnimator = SlideInDownAnimator()
            val linearLayoutManager = LinearLayoutManager(this.context)
            layoutManager = linearLayoutManager
            val scrollListener = object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
                override fun onLoadMore(
                    page: Int,
                    totalItemsCount: Int,
                    view: RecyclerView?
                ) {
                    // Triggered only when new data needs to be appended to the list
                    // Add whatever code is needed to append new items to the bottom of the list
                    transportListViewModel.loadNextData()
                }
            }
            addOnScrollListener(scrollListener)
        }
    }

    private fun configTransportAdapter() {
        val onItemShortTap = { position: Int -> showDetails(position)}
        val onItemLongTap = { position: Int -> delTransport(position)}
        transportAdapter = TransportAdapter(onItemShortTap, onItemLongTap)
        transportAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                if (positionStart == 0 && itemCount == 1) {
                    transportList.scrollToPosition(0)
                }
            }
        })
    }


    private fun observeViewModelState() {
        transportListViewModel.transports
            .observe(viewLifecycleOwner) {
                transportAdapter.items = it
            }
        transportListViewModel.showToast
            .observe(viewLifecycleOwner) {
                showToast(getString(R.string.item_deleted),requireContext())
            }
    }

}