package com.example.singleactivity.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.singleactivity.R
import com.example.singleactivity.intro.Intro
import com.example.singleactivity.intro.IntroAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.fragment_intro_viewpager.*
import kotlinx.android.synthetic.main.fragment_intro_viewpager.dots_indicator
import kotlinx.android.synthetic.main.fragment_intro_viewpager.viewPager

class IntroViewPagerFragment : Fragment(R.layout.fragment_intro_viewpager) {
    lateinit var introAdapter: IntroAdapter
    private val intros = listOf(
        Intro(
            R.drawable.ic_swap_horiz,
            R.string.intro1_text,
            R.color.intro1color
        ),
        Intro(
            R.drawable.ic_control_point,
            R.string.intro2_text,
            R.color.intro2color
        ),
        Intro(
            R.drawable.ic_filter_list,
            R.string.intro3_text,
            R.color.intro3color
        )
    )

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        introAdapter = IntroAdapter(
            this,
            intros
        )
        viewPager.adapter = introAdapter

        dots_indicator.setViewPager2(viewPager)

        closeButton.setOnClickListener {
            closeIntro()
        }

        skipButton.setOnClickListener {
            MaterialAlertDialogBuilder(context)
                .setTitle(resources.getString(R.string.skip_intro))
                .setPositiveButton(resources.getString(R.string.yes)) { _, _ ->
                    closeIntro()
                }
                .show()
        }
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == intros.lastIndex) {
                    skipButton.visibility = View.GONE
                    closeButton.visibility = View.VISIBLE
                }
            }
        })

    }


    private fun closeIntro() {
        fragmentManager?.beginTransaction()?.replace(
            R.id.app_fragment,
            ViewPagerFragment()
        )?.commit()
    }


}