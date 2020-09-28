package com.example.a12_lists.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.a12_lists.R
import com.example.a12_lists.model.Transport
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_aicraft.*

abstract class TransportHolder(
    final override val containerView: View,
    onItemShortTap: (position: Int) -> Unit,
    onItemLongTap: (position: Int) -> Boolean
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    init {
        containerView.setOnLongClickListener {
            onItemLongTap(adapterPosition)
        }
        containerView.setOnClickListener {
            onItemShortTap(adapterPosition)
        }
    }

    open fun bind(transport: Transport) {
        nameTV.text = transport.name
        maxSpeedTV.text = containerView.context.getString(R.string.max_speed_placeholder,transport.maxSpeed)
        Glide.with(itemView)
            .load(transport.avatarLink)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.ic_baseline_emoji_transportation)
            .error(R.drawable.ic_baseline_error_outline)
            .into(avatarIV)

    }

}