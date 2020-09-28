package com.skillbox.github.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.skillbox.github.R
import com.skillbox.github.model.GithubViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment: Fragment(R.layout.fragment_main) {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        GithubViewModel.setToolbarTitle(R.string.app_name)

        currentUserInfoButton.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToCurrentUserFragment())
        }

        repositoryListButton.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToRepositoryListFragment())
        }

    }

}