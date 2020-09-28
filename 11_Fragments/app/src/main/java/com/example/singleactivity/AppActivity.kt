package com.example.singleactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.singleactivity.fragments.IntroViewPagerFragment
import com.example.singleactivity.fragments.ViewPagerFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app)

        showViewPagerFragment()
    }

    private fun showViewPagerFragment() {
        if (supportFragmentManager.findFragmentById(R.id.app_fragment) == null) {
            supportFragmentManager.beginTransaction().add(R.id.app_fragment,
                IntroViewPagerFragment()
            ).commit()
        }
    }

}
