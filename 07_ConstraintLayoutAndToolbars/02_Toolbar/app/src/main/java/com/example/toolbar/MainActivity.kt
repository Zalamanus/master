package com.example.toolbar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initToolbar()

        val searchItem = toolbar.menu.findItem(R.id.actionSearch)
        searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                toast("Search expanded")
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                toast("Search collapsed")
                return true
            }

        })

        (searchItem.actionView as SearchView).setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val regex = query?.toRegex()
                val matchResult = regex?.findAll(getString(R.string.long_string))
                searchTextView.text = "${getString(R.string.matches_count)} ${matchResult?.count().toString()}"
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

    }

    private fun initToolbar() {
        toolbar.setNavigationOnClickListener {
            toast("NavClick")
        }

        toolbar.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId) {
                R.id.menuAction1 -> {
                    toast("Action 1 clicked")
                    true
                }
                R.id.menuAction2 -> {
                    toast("Action 2 clicked")
                    true
                }
                else -> false
            }
        }

    }

    private fun toast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT ).show()
    }
}
