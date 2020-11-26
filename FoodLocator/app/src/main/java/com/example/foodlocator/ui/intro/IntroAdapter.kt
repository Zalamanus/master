package com.example.foodlocator.ui.intro

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class IntroAdapter(
    fragment: Fragment,
    var intros: List<Fragment>
) : FragmentStateAdapter(fragment) {
    override fun getItemCount() = intros.size

    override fun createFragment(position: Int) = intros[position]
}