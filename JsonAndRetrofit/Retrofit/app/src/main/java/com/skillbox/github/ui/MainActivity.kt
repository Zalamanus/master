package com.skillbox.github.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.skillbox.github.R
import com.skillbox.github.model.GithubViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setActionBar(toolbar)

        GithubViewModel.toolbarTitle.observe(this) {
            toolbar.setTitle(it)
        }
    }

}