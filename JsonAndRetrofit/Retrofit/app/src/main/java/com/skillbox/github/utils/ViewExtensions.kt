package com.skillbox.github.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

fun ViewGroup.inflate(@LayoutRes layout: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layout, this, attachToRoot)
}

fun showToast(text: String, context: Context?) {
    android.widget.Toast.makeText(context, text, android.widget.Toast.LENGTH_LONG ).show()
}