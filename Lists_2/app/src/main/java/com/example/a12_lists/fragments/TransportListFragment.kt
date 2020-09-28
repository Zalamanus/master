package com.example.a12_lists.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a12_lists.*
import com.example.a12_lists.adapters.TransportAdapter
import com.example.a12_lists.model.Transport
import com.example.a12_lists.model.TransportType
import com.example.a12_lists.utils.AutoClearedValue
import com.example.a12_lists.utils.EndlessRecyclerViewScrollListener
import jp.wasabeef.recyclerview.animators.SlideInDownAnimator
import kotlinx.android.synthetic.main.fragment_transport_list.*
import kotlin.random.Random


open class TransportListFragment : Fragment(R.layout.fragment_transport_list) {

    var transportAdapter by AutoClearedValue<TransportAdapter>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initList()
        addFab.setOnClickListener { addTransport() }
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
        val newTransport =
            when (transportType) {
                TransportType.EARTH -> Transport.Car(
                    transportName,
                    maxSpeed,
                    typeRelatedParam
                )
                TransportType.AIR -> Transport.Aircraft(
                    transportName,
                    maxSpeed,
                    typeRelatedParam
                )
                TransportType.WATER -> Transport.Ship(
                    transportName,
                    maxSpeed,
                    typeRelatedParam
                )
            }
        emptyListTV.visibility = View.GONE
        transportAdapter.items = listOf(newTransport) + transportAdapter.items

    }

    fun delTransport(position: Int) {
        transportAdapter.items = transportAdapter.items.filterIndexed { index, _ -> index != position }
        if (transportAdapter.items.isEmpty()) emptyListTV.visibility = View.VISIBLE
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
                    loadNextDataFromApi()
                }
            }
            addOnScrollListener(scrollListener)
        }
    }

    fun configTransportAdapter() {
        transportAdapter =
            TransportAdapter { position ->
                delTransport(position)
            }
        transportAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                if (positionStart == 0) {
                    transportList.scrollToPosition(0)
                }
            }
        })
        transportAdapter.items = MainFragment.transports
    }

    private fun loadNextDataFromApi() {
        if (transportAdapter.items.count() < 50) {
            val additionalTransports = listOf(
                getOneMoreTransport()
                , getOneMoreTransport()
                , getOneMoreTransport()
                , getOneMoreTransport()
            )
            transportAdapter.items = transportAdapter.items + additionalTransports
        }
    }

    private fun getOneMoreTransport(): Transport {
        return when(Random.nextInt(TransportType.values().size)) {
            0 -> Transport.Car("Машина №${Random.nextInt(50)}", Random.nextInt(300), Random.nextInt(5))
            1 -> Transport.Aircraft("Самолет №${Random.nextInt(50)}", Random.nextInt(1000), Random.nextInt(6))
            else -> Transport.Ship("Корабль №${Random.nextInt(50)}", Random.nextInt(50), Random.nextInt(100000))
        }
    }

    override fun onPause() {
        super.onPause()
        MainFragment.transports = transportAdapter.items
    }

}