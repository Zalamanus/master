package com.example.singleactivity.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.example.singleactivity.R
import com.example.viewandlayout.withArguments
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.fragment_intro.*
import kotlinx.android.synthetic.main.fragment_page.*
import kotlinx.android.synthetic.main.fragment_page.imageView
import kotlinx.android.synthetic.main.fragment_page.textView

class IntroFragment : Fragment(R.layout.fragment_intro) {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        requireView().setBackgroundResource(requireArguments().getInt(KEY_COLOR))
        textView.setText(requireArguments().getInt(KEY_TEXT))
        imageView.setImageResource(requireArguments().getInt(KEY_DRAWABLE))

    }

    companion object {

        private const val KEY_DRAWABLE = "image"
        private const val KEY_TEXT = "text"
        private const val KEY_COLOR = "color"

        fun newInstance(
            @DrawableRes drawableRes: Int,
            @StringRes stringRes: Int,
            @ColorRes colorRes: Int

        ): IntroFragment {
            return IntroFragment().withArguments {
                putInt(KEY_DRAWABLE, drawableRes)
                putInt(KEY_TEXT, stringRes)
                putInt(KEY_COLOR, colorRes)
            }
        }
    }



}