package com.example.testjobalef.gridadapter

import androidx.recyclerview.widget.DiffUtil
import com.example.testjobalef.model.Photo
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class PhotoGridAdapter(
    onItemClicked: (photo: Photo) -> Unit
): AsyncListDifferDelegationAdapter<Photo>(ContactDiffUtilCallback()) {

    init {
        delegatesManager
            .addDelegate(PhotoAdapterDelegate(onItemClicked))

    }

    class ContactDiffUtilCallback: DiffUtil.ItemCallback<Photo>() {
        override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem.toString() == newItem.toString()
        }

    }
}