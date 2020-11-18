package com.example.foodlocator

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.foodlocator.model.FourSquareViewModel
import com.example.foodlocator.utils.toast
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStates
import kotlinx.android.synthetic.main.fragment_main.*
import permissions.dispatcher.PermissionRequest
import permissions.dispatcher.ktx.constructPermissionsRequest


class MainFragment: Fragment(R.layout.fragment_main) {

    private lateinit var viewModel: FourSquareViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = FourSquareViewModel(requireActivity().application)
        viewModel.venues.observe(viewLifecycleOwner) {
            it.forEach { venue ->
                Log.d("test", "Location = ${venue.location.address}")
            }
        }

        viewModel.location.observe(viewLifecycleOwner) {
            toast("Latitude = ${it.latitude}, longtitude = ${it.longitude}")
        }
        viewModel.locationTurnOnNeed.observe(viewLifecycleOwner) {
            // Cast to a resolvable exception.
            val resolvable = it as ResolvableApiException
            // Show the dialog by calling startResolutionForResult(),
            // and check the result in onActivityResult().
            resolvable.startResolutionForResult(
                requireActivity(),
                MainActivity.REQUEST_CHECK_SETTINGS
            )
        }



        makeRequestButton.setOnClickListener {
            viewModel.getVenues("Gorno-Altaisk", "trending")
        }

    }



    override fun onResume() {
        super.onResume()
        Handler(Looper.getMainLooper()).post {
            constructPermissionsRequest(
                Manifest.permission.ACCESS_FINE_LOCATION,
                onShowRationale = ::onContactPermissionShowRationale,
                onPermissionDenied = ::onContactPermissionDenied,
                onNeverAskAgain = ::onContactPermissionNeverAskAgain,
                requiresPermission = ::onLocationPermissionGranted

            ).launch()
        }
    }

    private fun onLocationPermissionGranted() {
        viewModel.getLocation()
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopLocationUpdates()
    }

    private fun onContactPermissionNeverAskAgain() {
        toast(R.string.enable_location_access)
    }

    private fun onContactPermissionDenied() {
        toast(R.string.location_access_denied)
    }

    private fun onContactPermissionShowRationale(permissionRequest: PermissionRequest) {
        permissionRequest.proceed()
    }


}