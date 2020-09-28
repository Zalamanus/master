package com.example.singleactivity.intro

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.singleactivity.fragments.IntroFragment

class IntroAdapter (
    fragment: Fragment,
    var intros: List<Intro>
    ): FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int {
            return intros.size
        }

        override fun createFragment(position: Int): Fragment {
            val intro = intros[position]
            return IntroFragment.newInstance(
                intro.drawableRes,
                intro.stringRes,
                intro.colorRes
            )
        }
    }