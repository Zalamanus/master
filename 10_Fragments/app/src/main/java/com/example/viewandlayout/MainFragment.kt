package com.example.viewandlayout

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.viewandlayout.dummy.DummyContent


class MainFragment : Fragment(R.layout.fragment_main),
    ListFragment.OnListFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tabletSize = resources.getBoolean(R.bool.isTablet)
        if (!tabletSize) {
            childFragmentManager
                .beginTransaction()
                .replace(R.id.mainFragment_container, ListFragment())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit()
        }
    }

    override fun onListFragmentInteraction(item: DummyContent.DummyItem?) {
        val detailsFragment = DetailsFragment.newInstance(item)
        childFragmentManager
            .beginTransaction()
            .replace(R.id.mainFragment_container, detailsFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
            .addToBackStack("1")
            .commit()
    }

}