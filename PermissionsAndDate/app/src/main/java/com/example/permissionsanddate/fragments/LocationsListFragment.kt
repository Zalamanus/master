package com.example.permissionsanddate.fragments

import android.Manifest
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.permissionsanddate.listadapter.LocationAdapter
import com.example.permissionsanddate.MainActivity.Companion.requestingLocationUpdates
import com.example.permissionsanddate.MyApplication
import com.example.permissionsanddate.R
import com.example.permissionsanddate.model.MyLocation
import com.example.permissionsanddate.utils.AutoClearedValue
import com.example.permissionsanddate.utils.OpenFragment
import com.google.android.gms.location.*
import kotlinx.android.synthetic.main.fragment_locations.*
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId

class LocationsListFragment : Fragment(R.layout.fragment_locations) {


    var locationAdapter by AutoClearedValue<LocationAdapter>()

    private lateinit var locationRequest: LocationRequest
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        locationRequest = LocationRequest.create()
            .setFastestInterval(1000)
            .setInterval(5000)
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        fusedLocationClient = LocationServices
            .getFusedLocationProviderClient(requireContext())
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            locationResult ?: return
            for (location in locationResult.locations) {
                val newLocation = MyLocation(Instant.now(), location)
                locationAdapter.items = listOf(newLocation) + locationAdapter.items
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initList()


        addLocationButton.setOnClickListener {
            requestingLocationUpdates = true
            getLocation()
        }

        stopLocationUpdateButton.setOnClickListener {
            requestingLocationUpdates = false
            stopLocationUpdates()
        }
    }

    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }


    private fun getLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            val needPermissionFragment = NeedPermissionFragment()
            (requireActivity() as OpenFragment).openFragment(needPermissionFragment)
        } else {
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )

//            LocationServices.getFusedLocationProviderClient(requireContext())
//                .lastLocation
//                .addOnSuccessListener {
//                    it?.let {
//                        val newLocation = MyLocation(Instant.now(), it)
//                        locationAdapter.items = listOf(newLocation) + locationAdapter.items
//                        showToast("локация добавлена", requireContext())
//                    } ?: showToast(getString(R.string.locationNotExist), requireContext())
//                }
//                .addOnCanceledListener {
//                    showToast(
//                        getString(R.string.requestLocationCanceled),
//                        requireContext()
//                    )
//                }
//                .addOnFailureListener { _ ->
//                    showToast(getString(R.string.requestLocationFailed), requireContext())
//
//                }

        }


    }

    private fun initList() {
        configTransportAdapter()
        with(locationsList) {
            adapter = locationAdapter
            val linearLayoutManager = LinearLayoutManager(this.context)
            layoutManager = linearLayoutManager
        }
    }

    private fun configTransportAdapter() {
        locationAdapter =
            LocationAdapter { position ->
                changeLocationTime(position)
            }
        locationAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                if (positionStart == 0) {
                    locationsList.scrollToPosition(0)
                }
            }
        })
        locationAdapter.items = MyApplication.locationsList
    }

    private fun changeLocationTime(position: Int) {
//        showToast("Меняем дату $position", requireContext())
        val oldDateTime = locationAdapter.items[position].time.atZone(ZoneId.systemDefault())
        DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                TimePickerDialog(
                    requireContext(),
                    { _, hour, minute ->
                        locationAdapter.items[position].time =
                            LocalDateTime.of(year, month + 1, dayOfMonth, hour, minute)
                                .atZone(ZoneId.systemDefault())
                                .toInstant()
                        locationAdapter.notifyItemChanged(position)
                    },
                    oldDateTime.hour,
                    oldDateTime.minute,
                    true
                )
                    .show()
            },
            oldDateTime.year,
            oldDateTime.monthValue - 1,
            oldDateTime.dayOfMonth
        ).show()

    }

    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
        MyApplication.locationsList = locationAdapter.items
    }

    override fun onResume() {
        super.onResume()
        if (requestingLocationUpdates) getLocation()
    }

}
