package com.skillbox.github.ui.current_user

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.skillbox.github.R
import com.skillbox.github.model.GitHubUser
import com.skillbox.github.model.GithubViewModel
import com.skillbox.github.model.UserToPatch
import kotlinx.android.synthetic.main.fragment_current_user.*

class CurrentUserFragment : Fragment(R.layout.fragment_current_user) {

    private lateinit var dropDownList: AutoCompleteTextView
    private var currentUser: GitHubUser? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        GithubViewModel.setToolbarTitle(R.string.profile)

        dropDownInit()

        GithubViewModel.currentUser.observe(viewLifecycleOwner) { currentUser ->
            setUserToUI(currentUser)
            this.currentUser = currentUser
        }

        GithubViewModel.error.observe(viewLifecycleOwner) { errorString ->
            Toast.makeText(context, errorString, Toast.LENGTH_LONG).show()
            currentUser?.let { setUserToUI(it) }
        }

        if (savedInstanceState == null)
            GithubViewModel.getCurrentUser()

        editButton.setOnClickListener {
            when (editButton.text) {
                getText(R.string.edit) -> {
                    GithubViewModel.currentUserButtonModeRes = R.string.save
                    setButtonMode()

                }
                getText(R.string.save) -> {
                    GithubViewModel.currentUserButtonModeRes = R.string.edit
                    setButtonMode()
                    val newName = nameET.editText?.text.toString().trim()
                    val newEmail = emailET.editText!!.text.toString().trim()
                    val newCompany = companyET.editText!!.text.toString().trim()
                    val newLocation = locationET.editText!!.text.toString().trim()
                    val newHireable = when (dropDownList.text.toString()) {
                        resources.getStringArray(R.array.hireable_types_array)[0] -> true
                        resources.getStringArray(R.array.hireable_types_array)[1] -> false
                        else -> null
                    }
                    GithubViewModel.updateUser(
                        UserToPatch(
                            newName,
                            newEmail,
                            newCompany,
                            newLocation,
                            newHireable
                        )
                    )
                }
            }
        }

    }

    private fun setButtonMode() {
        GithubViewModel.currentUserButtonModeRes?.let {
            editButton.text = getText(it)
            when (it) {
                R.string.edit -> setFieldsEditable(false)
                R.string.save -> setFieldsEditable(true)
            }
        }
    }

    private fun setFieldsEditable(editable: Boolean) {
        nameET.isEnabled = editable
        emailET.isEnabled = editable
        companyET.isEnabled = editable
        locationET.isEnabled = editable
        hireable_dropdown.isEnabled = editable
    }

    private fun setUserToUI(currentUser: GitHubUser) {
        Glide.with(this)
            .load(currentUser.avatar_url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.ic_baseline_portrait)
            .error(R.drawable.ic_baseline_error_outline)
            .into(avatarTV)
        loginET.editText!!.setText(currentUser.login)
        idET.editText!!.setText(currentUser.id.toString())
        publicReposET.editText!!.setText(currentUser.public_repos?.toString())
        nameET.editText!!.setText(currentUser.name)
        companyET.editText!!.setText(currentUser.company)
        locationET.editText!!.setText(currentUser.location)
        emailET.editText!!.setText(currentUser.email)
        when (currentUser.hireable) {
            true -> dropDownList.setText(
                resources.getStringArray(R.array.hireable_types_array)[0],
                false
            )
            false -> dropDownList.setText(
                resources.getStringArray(R.array.hireable_types_array)[1],
                false
            )
            null -> dropDownList.setText(
                resources.getStringArray(R.array.hireable_types_array)[1],
                false
            )
        }

    }

    private fun dropDownInit() {
        val hireableTypes = resources.getStringArray(R.array.hireable_types_array)
        val dropdownAdapter = ArrayAdapter(
            requireContext(),
            R.layout.item_hireable_dropdown,
            hireableTypes
        )
        dropDownList = (hireable_dropdown.editText as AutoCompleteTextView)
        dropDownList.setAdapter(dropdownAdapter)

    }


}