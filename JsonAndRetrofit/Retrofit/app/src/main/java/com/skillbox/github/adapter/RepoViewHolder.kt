package com.skillbox.github.adapter

import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.skillbox.github.R
import com.skillbox.github.model.GitHubRepo
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_repo.*

class RepoViewHolder(override val containerView: View, onItemClicked: (Int) -> Unit) : RecyclerView.ViewHolder(containerView),
    LayoutContainer {

    init {
        containerView.setOnClickListener { onItemClicked(adapterPosition) }
    }


    fun bind(repo: GitHubRepo) {
            ownerAvatarIV.isVisible = true
            Glide.with(itemView)
                .load(repo.owner.avatar_url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_baseline_portrait)
                .error(R.drawable.ic_baseline_error_outline)
                .into(ownerAvatarIV)

            repoNameTV.text = repo.name
            repoDescTV.text = repo.description
            repoIdTV.text = repo.id.toString()

    }


}