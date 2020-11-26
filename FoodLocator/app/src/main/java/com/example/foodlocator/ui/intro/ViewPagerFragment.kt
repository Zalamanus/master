package com.example.foodlocator.ui.intro

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.foodlocator.R
import com.example.foodlocator.model.FourSquareViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.fragment_intro_viewpager.*

class ViewPagerFragment: Fragment(R.layout.fragment_intro_viewpager) {
    private lateinit var viewModel: FourSquareViewModel
    lateinit var introAdapter: IntroAdapter
    private val intros = listOf(
        IntroFragment1(),IntroFragment2(),IntroFragment3()
    )

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = FourSquareViewModel(requireActivity().application)
        if (viewModel.isIntroDisabled()) closeIntro()
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
            MaterialAlertDialogBuilder(requireContext())
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
                    skipButton.isVisible = false
                    closeButton.isVisible = true
                }
            }
        })



    }

    private fun closeIntro() {
        viewModel.disableIntro()
        findNavController().navigate(ViewPagerFragmentDirections.actionViewPagerFragmentToMainFragment())
    }

}