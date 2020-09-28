package com.example.a12_lists.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.a12_lists.R
import com.example.a12_lists.model.Transport
import com.example.a12_lists.model.TransportType
import kotlinx.android.synthetic.main.fragment_details.*

class DetailsFragment: Fragment(R.layout.fragment_details) {
    private val args: DetailsFragmentArgs by navArgs()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val transportId = args.transport.id
        val transportName = args.transport.name
        val transportMaxSpeed = args.transport.maxSpeed
        val transportType = getString(args.transport.type.desc)
        val transportTypeRelatedParam: Int
        when (args.transport.type) {
            TransportType.AIR -> {
                transportTypeRelatedParam = (args.transport as Transport.Aircraft).engineCount
                transportTypeRelatedParamTV.text = getString(R.string.engine_count_placeholder,transportTypeRelatedParam)
            }
            TransportType.EARTH -> {
                transportTypeRelatedParam = (args.transport as Transport.Car).doorCount
                transportTypeRelatedParamTV.text = getString(R.string.door_count_placeholder,transportTypeRelatedParam)
            }
            TransportType.WATER -> {
                transportTypeRelatedParam = (args.transport as Transport.Ship).displacement
                transportTypeRelatedParamTV.text = getString(R.string.displacement_placeholder,transportTypeRelatedParam)
            }
        }

        titleTV.text = getString(R.string.details_title, transportId )
        transportNameTV.text = getString(R.string.transport_name_placeholder,transportName)
        transportMaxSpeedTV.text = getString(R.string.max_speed_placeholder,transportMaxSpeed)
        transportTypeTV.text = getString(R.string.transport_type_placeholder,transportType)


    }
}