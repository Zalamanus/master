package com.example.viewandlayout

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FormState(val valid: Boolean, val message: String): Parcelable {
    override fun toString(): String {
        return message
    }
}