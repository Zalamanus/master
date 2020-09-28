package com.skillbox.github.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.skillbox.github.model.GitHubRepo

class ReposAdapter(
    onItemClicked: (position: Int) -> Unit
) : AsyncListDifferDelegationAdapter<GitHubRepo>(REPO_COMPARATOR) {

    init {
        delegatesManager
            .addDelegate(ReposAdapterDelegate(onItemClicked))

    }

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<GitHubRepo>() {
            override fun areItemsTheSame(oldItem: GitHubRepo, newItem: GitHubRepo): Boolean =
                oldItem.id == newItem.id

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: GitHubRepo, newItem: GitHubRepo): Boolean =
                oldItem == newItem
        }
    }
}