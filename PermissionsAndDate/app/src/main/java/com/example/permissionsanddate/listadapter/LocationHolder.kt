package com.example.permissionsanddate.listadapter


import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.permissionsanddate.R
import com.example.permissionsanddate.model.MyLocation
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_location.*
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter


class LocationHolder(override val containerView: View, onItemClicked: (position: Int) -> Unit) :
    RecyclerView.ViewHolder(containerView),
    LayoutContainer {

    init {
        containerView.setOnClickListener { onItemClicked(adapterPosition) }
    }

    private val formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy")
        .withZone(ZoneId.systemDefault())

    fun bind(location: MyLocation) {
        locationInfoTV.text = containerView.context.getString(
            R.string.location_info,
            location.data.latitude,
            location.data.longitude,
            location.data.speed,
            location.data.accuracy
            )


        timeTV.text = formatter.format(location.time)
        Glide.with(imageIV)
            .load(location.imageLink)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.ic_baseline_map)
            .error(R.drawable.ic_baseline_error)
            .into(imageIV)

    }
}
