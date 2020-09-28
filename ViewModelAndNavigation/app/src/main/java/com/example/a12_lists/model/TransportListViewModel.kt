package com.example.a12_lists.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.a12_lists.utils.SingleLiveEvent

class TransportListViewModel : ViewModel() {

    private val repository = TransportRepository()

    private val transportLiveData = MutableLiveData<List<Transport>>(repository.getTransportList())

    val transports: LiveData<List<Transport>>
        get() = transportLiveData

    private val showToastLiveData = SingleLiveEvent<Unit>()

    val showToast: LiveData<Unit>
        get() = showToastLiveData

    fun addTransport(
        transportName: String,
        transportType: TransportType,
        maxSpeed: Int,
        typeRelatedParam: Int
    ) {
        repository.addTransport(transportName, transportType, maxSpeed, typeRelatedParam)
        updateTransportList()
    }

    fun delTransport(position: Int) {
        repository.delTransport(position)
        updateTransportList()
        showToastLiveData.postValue(Unit)
    }

    fun loadNextData() {
        repository.loadNextDataFromApi()
        updateTransportList()
    }

    private fun updateTransportList() {
        transportLiveData.postValue(repository.getTransportList())
    }


}