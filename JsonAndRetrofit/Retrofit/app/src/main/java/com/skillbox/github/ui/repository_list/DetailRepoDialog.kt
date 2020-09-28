package com.skillbox.github.ui.repository_list

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.observe
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.textfield.TextInputLayout
import com.skillbox.github.R
import com.skillbox.github.model.GitHubRepo
import com.skillbox.github.model.GithubViewModel

class DetailRepoDialog : DialogFragment(

) {
    private lateinit var ownerAvatarIV: ImageView
    private lateinit var starredRepoIV: ImageView
    private lateinit var idET: TextInputLayout
    private lateinit var repoNameET: TextInputLayout
    private lateinit var repoFullNameET: TextInputLayout
    private lateinit var repoDescET: TextInputLayout
    private lateinit var repoHtmlUrlET: TextInputLayout
    private lateinit var repoSshUrlET: TextInputLayout

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            // Get the layout inflater
            val inflater = requireActivity().layoutInflater
            val view = inflater.inflate(R.layout.dialog_repo_details, null)
            ownerAvatarIV = view.findViewById(R.id.ownerAvatarIV)
            starredRepoIV = view.findViewById(R.id.starredRepoIV)
            idET = view.findViewById(R.id.idET)
            repoNameET = view.findViewById(R.id.repoNameET)
            repoFullNameET = view.findViewById(R.id.repoFullNameET)
            repoDescET = view.findViewById(R.id.repoDescET)
            repoHtmlUrlET = view.findViewById(R.id.repoHtmlUrlET)
            repoSshUrlET = view.findViewById(R.id.repoSshUrlET)



            builder.setView(view)
                // Add action buttons
                .setPositiveButton(getString(R.string.ok)) { dialog, _ ->
                    dialog.cancel()
                }

            GithubViewModel.currentRepo.observe(this) { currentRepo ->
                setRepoToUI(currentRepo)
            }

            starredRepoIV.setOnClickListener {
                GithubViewModel.reverseStar()
            }
            builder.create()


        } ?: throw IllegalStateException("Activity cannot be null")
    }

    private fun setRepoToUI(currentRepo: GitHubRepo) {
        Glide.with(this)
            .load(currentRepo.owner.avatar_url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.ic_baseline_portrait)
            .error(R.drawable.ic_baseline_error_outline)
            .into(ownerAvatarIV)
        repoNameET.editText!!.setText(currentRepo.name)
        repoFullNameET.editText!!.setText(currentRepo.full_name)
        idET.editText!!.setText(currentRepo.id.toString())
        repoHtmlUrlET.editText!!.setText(currentRepo.html_url)
        currentRepo.description.orEmpty().let {
            if (it.isNotEmpty()) {
                repoDescET.editText!!.setText(it)
                repoDescET.isVisible = true
            } else repoDescET.isVisible = false
        }
        currentRepo.ssh_url.orEmpty().let {
            if (it.isNotEmpty()) {
                repoSshUrlET.editText!!.setText(it)
                repoSshUrlET.isVisible = true
            } else repoSshUrlET.isVisible = false
        }
        currentRepo.starred.let {
            when (it) {
                true -> starredRepoIV.setImageResource(R.drawable.ic_baseline_star_full)
                else -> starredRepoIV.setImageResource(R.drawable.ic_baseline_star_hollow)
            }
        }
    }


}