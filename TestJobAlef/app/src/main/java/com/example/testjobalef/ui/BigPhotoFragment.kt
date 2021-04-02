package com.example.testjobalef.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.testjobalef.R
import com.example.testjobalef.databinding.FragmentPhotoBinding
import com.example.testjobalef.utils.ViewBindingFragment

class BigPhotoFragment: ViewBindingFragment<FragmentPhotoBinding>(FragmentPhotoBinding::inflate) {
    private val args: BigPhotoFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(this)
            .load(args.url)
            .error(R.drawable.ic_baseline_error_outline)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.imageView)
    }
}