package com.example.testjobalef.ui

import android.os.Bundle
import android.view.Display
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.testjobalef.R
import com.example.testjobalef.databinding.FragmentPhotoGridBinding
import com.example.testjobalef.gridadapter.PhotoGridAdapter
import com.example.testjobalef.model.Photo
import com.example.testjobalef.model.PhotoViewModel
import com.example.testjobalef.utils.AutoClearedValue
import com.example.testjobalef.utils.ViewBindingFragment
import com.example.testjobalef.utils.toast


class PhotoGridFragment :
    ViewBindingFragment<FragmentPhotoGridBinding>(FragmentPhotoGridBinding::inflate) {
    private val viewModel by viewModels<PhotoViewModel>()
    private var photoAdapter by AutoClearedValue<PhotoGridAdapter>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        bindViewModel()


    }

    private fun initList() {
        val tabletSize = resources.getBoolean(R.bool.isTablet)
        val columnsCount = if (tabletSize) 3 else 2
        photoAdapter = PhotoGridAdapter(::onItemClick)
        binding.photoGridRV.apply {
            layoutManager = GridLayoutManager(requireContext(), columnsCount)
            adapter = photoAdapter
            setHasFixedSize(true)
        }
        viewModel.loadList()

        binding.swipeContainer.setOnRefreshListener {
            photoAdapter.items = emptyList()
            viewModel.loadList()
        }
    }

    private fun onItemClick(photo: Photo) {
        findNavController().navigate(
            PhotoGridFragmentDirections.actionPhotoGridFragmentToBigPhotoFragment(photo.url)
        )
    }

    private fun bindViewModel() {
        viewModel.photoList.observe(viewLifecycleOwner) {
            photoAdapter.items = it
            binding.swipeContainer.isRefreshing = false
        }
        viewModel.error.observe(viewLifecycleOwner) {
            toast(it)
        }
    }

}