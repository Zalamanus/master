package com.skillbox.github.adapter

import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.skillbox.github.R
import com.skillbox.github.model.GitHubRepo
import com.skillbox.github.utils.inflate

class ReposAdapterDelegate(
    private val onItemClicked: (position: Int) -> Unit
) : AbsListItemAdapterDelegate<GitHubRepo, GitHubRepo, RepoViewHolder>() {
    override fun isForViewType(
        item: GitHubRepo,
        items: MutableList<GitHubRepo>,
        position: Int
    ): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup): RepoViewHolder {
        return RepoViewHolder(parent.inflate(R.layout.item_repo),onItemClicked)

    }

    override fun onBindViewHolder(
        item: GitHubRepo,
        holder: RepoViewHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }

}
