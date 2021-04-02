package com.example.testjobalef.gridadapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.testjobalef.R
import com.example.testjobalef.databinding.ItemPhotoBinding
import com.example.testjobalef.model.Photo
import com.example.testjobalef.utils.inflate
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import kotlin.properties.Delegates

class PhotoAdapterDelegate(private val onItemClicked: (photo: Photo) -> Unit
) :
    AbsListItemAdapterDelegate<Photo, Photo, PhotoAdapterDelegate.PhotoHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup): PhotoHolder {
        return PhotoHolder(
            parent.inflate(R.layout.item_photo),
            onItemClicked
        )
    }


    override fun isForViewType(
        item: Photo,
        items: MutableList<Photo>,
        position: Int
    ): Boolean {
        return true
    }

    override fun onBindViewHolder(
        item: Photo,
        holder: PhotoHolder,
        payloads: MutableList<Any>
    ) {
        holder.photo = item
        holder.bind(item)
    }

    class PhotoHolder(
        containerView: View,
        onItemClicked: (photo: Photo) -> Unit,
        var mItem: Photo? = null
    ) :
        RecyclerView.ViewHolder(containerView) {
        private val binding = ItemPhotoBinding.bind(containerView)
        var photo by Delegates.notNull<Photo>()

        init {
            binding.root.setOnClickListener { onItemClicked(photo) }
        }

        fun bind(item: Photo) {
            mItem = item
            Glide.with(itemView)
                .load(item.url)
                .error(R.drawable.ic_baseline_error_outline)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.imageView)
        }


    }


}