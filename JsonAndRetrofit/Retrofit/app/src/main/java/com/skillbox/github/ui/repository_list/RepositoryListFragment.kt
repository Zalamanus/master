package com.skillbox.github.ui.repository_list

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.skillbox.github.R
import com.skillbox.github.adapter.ReposAdapter
import com.skillbox.github.model.GithubViewModel
import com.skillbox.github.utils.AutoClearedValue
import kotlinx.android.synthetic.main.fragment_repos_list.*

class RepositoryListFragment : Fragment(R.layout.fragment_repos_list) {

    private var reposAdapter by AutoClearedValue<ReposAdapter>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        GithubViewModel.setToolbarTitle(R.string.public_repos_list)

        GithubViewModel.publicRepos.observe(viewLifecycleOwner) { repos ->
            reposAdapter.items = repos
        }

        GithubViewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        }

        initList()
        if (savedInstanceState == null) search()
    }

    private fun search() {
        GithubViewModel.getPublicRepos()
    }

    private fun initList() {
        reposAdapter = ReposAdapter { position ->
            showRepoDetails(position)
        }
        val divider = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        with(reposList) {
            adapter = reposAdapter
            addItemDecoration(divider)
            val linearLayoutManager = LinearLayoutManager(context)
            layoutManager = linearLayoutManager

        }
    }

    private fun showRepoDetails(position: Int) {
        GithubViewModel.setCurrentRepo(position)
        val newDialog =
            DetailRepoDialog()
        newDialog.show(childFragmentManager, "detailsRepo_dialog")
    }

}