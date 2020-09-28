package com.example.singleactivity.intro

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Intro (
    @DrawableRes val drawableRes: Int,
    @StringRes val stringRes: Int,
    @ColorRes val colorRes: Int
) {
}