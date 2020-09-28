package com.example.a12_lists.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.a12_lists.*
import com.example.a12_lists.model.Transport
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment(R.layout.fragment_main) {
    companion object {
        var transports = listOf(
            Transport.Aircraft("Ан-2", 200, 1),
            Transport.Car("Альфа-Ромео", 250, 2),
            Transport.Ship("Титаник", 42, 52310),
            Transport.Car("Ваз-2106", 150, 4),
            Transport.Aircraft("Beechcraft Baron", 350, 2)
        )

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        transportVerticalListButton.setOnClickListener {
            startFragment(TransportListFragment())
        }

        transportHorizontalListButton.setOnClickListener {
            startFragment(TransportHorizontalListFragment())
        }

        transportGridListButton.setOnClickListener {
            startFragment(TransportGridFragment())
        }

        transportStaggeredGridListButton.setOnClickListener {
            startFragment(TransportStaggeredGridFragment())

        }
    }

    private fun startFragment(fragment: Fragment) {
        (activity as OpenFragment).openFragment(fragment)
    }
}
