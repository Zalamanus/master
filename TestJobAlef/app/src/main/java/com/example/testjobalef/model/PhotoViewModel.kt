package com.example.testjobalef.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testjobalef.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class PhotoViewModel : ViewModel() {

    private val repository = PhotoRepository()

    private val photoListMutableLiveData = MutableLiveData<List<Photo>>()
    val photoList: LiveData<List<Photo>>
        get() = photoListMutableLiveData

    private val errorMutableLiveData = SingleLiveEvent<String>()
    val error: LiveData<String>
        get() = errorMutableLiveData

    fun loadList() {
        viewModelScope.launch {
            try {
                photoListMutableLiveData.postValue(repository.loadList(URL))
            } catch (t: Throwable) {
                photoListMutableLiveData.postValue(emptyList())
                errorMutableLiveData.postValue("Some error occurred. Check your internet connection")
            }
        }
    }

    companion object {
        const val URL = "http://dev-tasks.alef.im/task-m-001/list.php"
    }
}