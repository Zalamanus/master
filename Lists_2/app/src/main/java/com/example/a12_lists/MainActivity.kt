package com.example.a12_lists

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.a12_lists.fragments.MainFragment
import com.example.a12_lists.fragments.OpenFragment

class MainActivity : AppCompatActivity(), OpenFragment {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            val mainFragment = MainFragment()
            supportFragmentManager.beginTransaction().replace(R.id.mainContainer, mainFragment)
                .setPrimaryNavigationFragment(mainFragment)
                .commit()
        }
    }

    override fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(
            R.id.mainContainer,
            fragment
        )
            .addToBackStack("1")
            .commit()
    }


}
