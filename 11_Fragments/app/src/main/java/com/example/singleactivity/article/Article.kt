package com.example.singleactivity.article

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Article(
    @DrawableRes val drawableRes: Int,
    @StringRes val stringRes: Int,
    val acticleTags: List<ArticleTag>
    ) {
    var badgeNumber: Int = 0
}