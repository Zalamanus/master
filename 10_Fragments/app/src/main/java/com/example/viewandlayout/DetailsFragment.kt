package com.example.viewandlayout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.viewandlayout.dummy.DummyContent
import kotlinx.android.synthetic.main.fragment_details.*

private const val ARG_PARAM1 = "param1"

class DetailsFragment : Fragment() {
    private var param1: DummyContent.DummyItem? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getSerializable(ARG_PARAM1) as DummyContent.DummyItem
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        detailsTextView.text = param1?.details
    }

    companion object {
        @JvmStatic fun newInstance(item: DummyContent.DummyItem?) =
                DetailsFragment().apply {
                    arguments = Bundle().apply {
                        putSerializable(ARG_PARAM1, item)
                    }
                }
    }
}
