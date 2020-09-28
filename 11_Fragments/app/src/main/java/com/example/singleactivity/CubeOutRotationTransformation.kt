package com.example.singleactivity

import android.view.View
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs


class CubeOutRotationTransformation : ViewPager2.PageTransformer {
    override fun transformPage(
        page: View,
        position: Float
    ) {
        val deltaY = 0.5F
        page.pivotX = if (position < 0F) page.width.toFloat() else 0F
        page.pivotY = page.height * deltaY
        page.rotationY = 70F * position
    }
}