package com.example.foodlocator.model

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.example.foodlocator.R

class CustomMarker(
    val latitude: Double?,
    val longitude: Double?,
    val title: String,
    val address: String?,
    val drawable: Int,
    val tint: Int
) {
    private constructor(builder: Builder) : this(
        builder.latitude,
        builder.longitude,
        builder.title,
        builder.address,
        builder.drawableRes,
        builder.tint

    )

    class Builder {
        var latitude: Double? = null
            private set
        var longitude: Double? = null
            private set
        var title: String = "undefined"
            private set
        var address: String? = null
            private set

        @DrawableRes
        var drawableRes: Int = R.drawable.ic_baseline_not_listed_location
            private set
        @ColorRes
        var tint: Int = R.color.white
            private set

        fun latitude(value: Double) = apply { latitude = value }
        fun longitude(value: Double) = apply { longitude = value }
        fun title(value: String) = apply { title = value }
        fun address(value: String?) = apply { address = value }
        fun drawableRes(value: Int) = apply { drawableRes = value }
        fun tint(value: Int) = apply { tint = value }
        fun build() = CustomMarker(this)
    }
}