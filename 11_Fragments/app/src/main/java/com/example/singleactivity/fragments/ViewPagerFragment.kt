package com.example.singleactivity.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.singleactivity.CubeOutRotationTransformation
import com.example.singleactivity.R
import com.example.singleactivity.article.Article
import com.example.singleactivity.article.ArticleAdapter
import com.example.singleactivity.article.ArticleTag
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_viewpager.*
import kotlin.random.Random

class ViewPagerFragment : Fragment(R.layout.fragment_viewpager), PageFragment.PageListener {
    lateinit var viewPagerAdapter: ArticleAdapter
    private val articles = listOf(
        Article(
            R.drawable.ic_filter_1,
            R.string.article1_text,
            listOf(ArticleTag.NEWS, ArticleTag.TECH)
        ),
        Article(
            R.drawable.ic_filter_2,
            R.string.article2_text,
            listOf(ArticleTag.POLITICS, ArticleTag.TECH)
        ),
        Article(
            R.drawable.ic_filter_3,
            R.string.article3_text,
            listOf(ArticleTag.POLITICS)
        ),
        Article(
            R.drawable.ic_filter_4,
            R.string.article4_text,
            listOf(ArticleTag.NEWS, ArticleTag.POLITICS)
        ),
        Article(
            R.drawable.ic_filter_5,
            R.string.article5_text,
            listOf(ArticleTag.TECH)
        )
    )

    private var articlesToShow = listOf<Article>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        articlesToShow = filterArticles(articles)
        viewPagerAdapter = ArticleAdapter(
            this,
            articlesToShow
        )
        viewPager.adapter = viewPagerAdapter
        viewPager.setPageTransformer(CubeOutRotationTransformation())

        dots_indicator.setViewPager2(viewPager)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.setText(articlesToShow[position].stringRes)
        }.attach()

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabLayout.getTabAt(position)?.removeBadge()
                articlesToShow[position].badgeNumber = 0
            }
        })

        toolBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.filter_action -> {
                    showDialog()
                    true
                }

                else -> false
            }
        }
    }

    private fun filterArticles(articles: List<Article>): List<Article> {
        val articleSet = mutableSetOf<Article>()
        ArticleTag.values().filter {
            it.checked
        }.forEach { articleTag ->
            articles.forEach { article ->
                if (article.acticleTags.contains(articleTag)) articleSet.add(article)
            }
        }

        return articleSet.sortedBy {
            resources.getString(it.stringRes)
        }.toList()
    }

    private fun showDialog() {
        val multiItems = arrayOf(
            resources.getString(ArticleTag.NEWS.textRes),
            resources.getString(ArticleTag.TECH.textRes),
            resources.getString(ArticleTag.POLITICS.textRes)
        )
        val checkedItems = BooleanArray(ArticleTag.values().size) {
            ArticleTag.values().get(it).checked
        }

        MaterialAlertDialogBuilder(context)
            .setTitle(resources.getString(R.string.filter_title))
            .setNeutralButton(resources.getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton(resources.getString(R.string.ok)) { _, _ ->
                // Respond to positive button press
                checkedItems.forEachIndexed { index, itemChecked ->
                    ArticleTag.values()[index].checked = itemChecked
                }
                articlesToShow = filterArticles(articles)
                viewPagerAdapter.articles = articlesToShow
                viewPager.adapter = viewPagerAdapter // this is need to destroy viewpager's cache
                viewPagerAdapter.notifyDataSetChanged()
                refreshBadges()
            }
            // Single-choice items (initialized with checked item)
            .setMultiChoiceItems(multiItems, checkedItems) { _, which, checked ->
                checkedItems[which] = checked
            }

            .show()
    }

    private fun refreshBadges() {
        articlesToShow.forEachIndexed { index, article ->
            if (article.badgeNumber < 1) {
                tabLayout.getTabAt(index)?.removeBadge()
            } else {
                tabLayout.getTabAt(index)?.orCreateBadge?.apply {
                    number = article.badgeNumber
                }
            }


        }
    }

    override fun onMakeEventClicked() {
        val randomPosition = Random.nextInt(articlesToShow.size)
        if (tabLayout.selectedTabPosition == randomPosition) {
            onMakeEventClicked()
            return
        }
        tabLayout.getTabAt(randomPosition)?.orCreateBadge?.apply {
            articlesToShow[randomPosition].badgeNumber += 1
            number = articlesToShow[randomPosition].badgeNumber
        }
    }

}