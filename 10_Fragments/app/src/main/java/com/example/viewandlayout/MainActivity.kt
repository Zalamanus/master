package com.example.viewandlayout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.example.viewandlayout.dummy.DummyContent

const val IMAGE_URL = "https://hackernoon.com/drafts/h7yw28l8.png"

class MainActivity : AppCompatActivity(),ListFragment.OnListFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showLoginFragment()
    }

    private fun showLoginFragment() {
        if (supportFragmentManager.findFragmentById(R.id.frame_container) == null) {
            supportFragmentManager.beginTransaction().add(R.id.frame_container, LoginFragment())
                .commit()
        }
    }

    override fun onListFragmentInteraction(item: DummyContent.DummyItem?) {
        val detailsFragment = DetailsFragment.newInstance(item)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.mainFragment_container,detailsFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .addToBackStack(null)
            .commit()
    }

}
