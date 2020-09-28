package com.example.singleactivity.article

import androidx.annotation.StringRes
import com.example.singleactivity.R

enum class ArticleTag(@StringRes val textRes: Int) {
    NEWS(R.string.article_tag_news),
    TECH (R.string.article_tag_tech),
    POLITICS (R.string.article_tag_politics);

    var checked = true
}