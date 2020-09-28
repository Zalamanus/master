package com.example.a12_lists.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.util.DisplayMetrics
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ItemDecoration(private val context: Context): RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val offset = 10.toPixels()
        with(outRect) {
            top = offset
            bottom = offset
            left = offset
            right = offset
        }
    }
    private fun Int.toPixels(): Int {
        val density = context.resources.displayMetrics.densityDpi
        val pixelsInDp = density / DisplayMetrics.DENSITY_DEFAULT
        return this * pixelsInDp

    }
}