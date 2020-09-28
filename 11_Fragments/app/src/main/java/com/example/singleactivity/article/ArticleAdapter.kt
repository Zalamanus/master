package com.example.singleactivity.article

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.singleactivity.fragments.PageFragment

class ArticleAdapter(
    fragment: Fragment,
    var articles: List<Article>
): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return articles.size
    }

    override fun createFragment(position: Int): Fragment {
        val article = articles[position]
        return PageFragment.newInstance(
            article.drawableRes,
            article.stringRes
        )
    }
}