package com.example.singleactivity.fragments

import android.os.Bundle
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.example.singleactivity.R
import com.example.viewandlayout.withArguments
import kotlinx.android.synthetic.main.fragment_page.*

class PageFragment: Fragment(R.layout.fragment_page) {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        textView.setText(requireArguments().getInt(KEY_TEXT))
        imageView.setImageResource(requireArguments().getInt(KEY_DRAWABLE))

        makeEventButton.setOnClickListener {
            showBadge()
        }
    }

    private fun showBadge() {
        (parentFragment as PageListener).onMakeEventClicked()
    }

    companion object {

        internal const val KEY_DRAWABLE = "image"
        internal const val KEY_TEXT = "text"

        fun newInstance(
            @DrawableRes drawableRes: Int,
            @StringRes stringRes: Int

        ): PageFragment {
            return PageFragment().withArguments {
                putInt(KEY_DRAWABLE, drawableRes)
                putInt(KEY_TEXT, stringRes)
            }
        }
    }

    interface PageListener {
        fun onMakeEventClicked()
    }
}