package com.skillbox.multithreading.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.skillbox.multithreading.R
import com.skillbox.multithreading.model.ThreadingViewModel
import kotlinx.android.synthetic.main.fragment_race_condition.*

class RaceConditionFragment : Fragment(R.layout.fragment_race_condition) {

    private val viewModel: ThreadingViewModel by viewModels()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        incrementButton.setOnClickListener {
            viewModel.incrementValue(
                Integer.parseInt(threadsNumberET.text.toString()),
                Integer.parseInt(incrementET.text.toString()),
                false
                )
        }
        incrementWSyncButton.setOnClickListener {
            viewModel.incrementValue(
                Integer.parseInt(threadsNumberET.text.toString()),
                Integer.parseInt(incrementET.text.toString()),
                true
            )
        }
        viewModel.incrementResult.observe(viewLifecycleOwner) {
            resultTV.text = it
        }
    }

}