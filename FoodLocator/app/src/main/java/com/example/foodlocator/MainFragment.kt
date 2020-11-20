package com.example.foodlocator

import android.Manifest
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.example.foodlocator.model.FourSquareViewModel
import com.example.foodlocator.utils.toast
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapFragment
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_main.*
import permissions.dispatcher.PermissionRequest
import permissions.dispatcher.ktx.constructPermissionsRequest


class MainFragment: Fragment(R.layout.fragment_main), OnMapReadyCallback {

    private lateinit var viewModel: FourSquareViewModel
//    private lateinit var binding: FragmentMainBinding

    private var googleMap: GoogleMap? = null

//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        binding = FragmentMainBinding.inflate(inflater)
//        return binding.root
//    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = FourSquareViewModel(requireActivity().application)

        bindViewModel()

        makeRequestButton.setOnClickListener {
            viewModel.getVenues("Gorno-Altaisk", "trending")
        }

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createMapView()
    }

    /**
     * Initialises the mapview
     */
    private fun createMapView() {
        /**
         * Catch the null pointer exception that
         * may be thrown when initialising the map
         */
        try {
            if (null == googleMap) {
                //todo Надо найти mapView
                (mapView as MapFragment).getMapAsync {
                    googleMap = it
                }
                /**
                 * If the map is still null after attempted initialisation,
                 * show an error to the user
                 */
//                if (null == googleMap) {
//                    toast(getString(com.example.foodlocator.R.string.map_initialization_error))
//                }
            }
        } catch (exception: NullPointerException) {
            Log.e("mapApp", exception.toString())
        }
    }

    private fun addMeMarker(location: Location) {
        val lat = location.latitude
        val lng = location.longitude
        //устанавливаем позицию и масштаб отображения карты
        val cameraPosition = CameraPosition.Builder()
            .target(LatLng(lat, lng))
            .zoom(15f)
            .build()
        val cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition)
        googleMap?.animateCamera(cameraUpdate)
        if (null != googleMap) {
            googleMap!!.addMarker(
                MarkerOptions()
                    .position(LatLng(lat, lng))
                    .title("Me")
                    .draggable(false)
            )
        }
    }


    private fun bindViewModel() {
        viewModel.venues.observe(viewLifecycleOwner) {
            it.forEach { venue ->
                Log.d("test", "Location = ${venue.location.address}")
            }
        }
        viewModel.myLocation.observe(viewLifecycleOwner) {
            toast("Latitude = ${it.latitude}, longtitude = ${it.longitude}")
            addMeMarker(it)
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
    }


    private fun onLocationPermissionGranted() {
        viewModel.getLocation()
    }

    override fun onStop() {
        super.onStop()
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

    override fun onMapReady(p0: GoogleMap?) {
        googleMap = p0
    }


}