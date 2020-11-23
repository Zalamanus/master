package com.example.foodlocator

import android.Manifest
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.foodlocator.model.CustomMarker
import com.example.foodlocator.model.FourSquareViewModel
import com.example.foodlocator.utils.toast
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.fragment_main.*
import permissions.dispatcher.PermissionRequest
import permissions.dispatcher.ktx.constructPermissionsRequest
import kotlin.math.roundToInt


class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var viewModel: FourSquareViewModel

    private var googleMap: GoogleMap? = null
    private var meMarker: Marker? = null
    private var needToCenter = true
    private val venueMarkers = mutableSetOf<Marker>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.venues_type_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_food -> {
                showVenues(FourSquareViewModel.FOOD_SECTION)
                true
            }
            R.id.menu_coffee -> {
                showVenues(FourSquareViewModel.COFFEE_SECTION)
                true
            }
            R.id.menu_shops -> {
                showVenues(FourSquareViewModel.SHOPS_SECTION)
                true
            }
            R.id.menu_trending -> {
                showVenues(FourSquareViewModel.TRENDING_SECTION)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = FourSquareViewModel(requireActivity().application)

        bindViewModel()

        showMyLocationButton.setOnClickListener {
            if (meMarker == null) {
                viewModel.getFineLocation()
                needToCenter = true
            } else
                centerOnMarker(meMarker!!)
        }

        Handler(Looper.getMainLooper()).post {
            constructPermissionsRequest(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                onShowRationale = ::onContactPermissionShowRationale,
                onPermissionDenied = ::onContactPermissionDenied,
                onNeverAskAgain = ::onContactPermissionNeverAskAgain,
                requiresPermission = ::onLocationPermissionGranted
            ).launch()
        }


    }

    private fun showVenues(section: String) {
        val latitude = googleMap?.cameraPosition?.target?.latitude.toString()
        val longitude = googleMap?.cameraPosition?.target?.longitude.toString()
        val radius = googleMap?.cameraPosition?.zoom?.let {
            (100000 / it).roundToInt()
        }
        viewModel.getVenues("$latitude,$longitude", section, radius)
    }

    private fun centerOnMarker(marker: Marker) {
        val cameraPosition = CameraPosition.Builder()
            .target(marker.position)
            .zoom(13f)
            .build()
        val cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition)
        googleMap?.animateCamera(cameraUpdate)

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
                val mapView = childFragmentManager.findFragmentById(R.id.mapView)
                (mapView as SupportMapFragment).getMapAsync {
                    googleMap = it
                }
            }
        } catch (exception: NullPointerException) {
            Log.e("mapApp", exception.toString())
        }
    }


    private fun bindViewModel() {
        viewModel.venueMarkers.observe(viewLifecycleOwner) { markers ->
            venueMarkers.forEach { marker ->
                marker.remove()
            }
            markers.forEach { customMarker ->
                addMarkerToMap(customMarker)?.let { marker ->
                    venueMarkers.add(marker)
                }
            }
            Log.d("test", "${markers.size}")
        }

        viewModel.userMarker.observe(viewLifecycleOwner) {
            it?.let {customMarker ->
                addMarkerToMap(customMarker)?.let { marker ->
                    meMarker?.remove()
                    meMarker = marker
                    if (needToCenter) {
                        centerOnMarker(marker)
                        needToCenter = false
                    }
                }

                //Показ заведений с задержкой для перемещения камеры
                Handler(Looper.getMainLooper()).postDelayed(
                    { showVenues(viewModel.getDefaultSection()) },
                    2000
                )

            }
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

    private fun addMarkerToMap(marker: CustomMarker): Marker? {
        val lat = marker.latitude ?: return null
        val lng = marker.longitude ?: return null
        if (null != googleMap) {
            return googleMap!!.addMarker(
                MarkerOptions()
                    .icon(
                        BitmapDescriptorFactory.fromBitmap(
                            getBitmapFromVectorDrawable(marker.drawable,marker.tint)
                        )
                    )
                    .position(LatLng(lat, lng))
                    .title(marker.title)
                    .draggable(false)
            )
        }
        return null
    }


    private fun onLocationPermissionGranted() {
        showMyLocationButton.isVisible = true
        viewModel.getCoarseLocation()
    }

    override fun onStop() {
        super.onStop()
        viewModel.stopLocationUpdates()
    }


    private fun onContactPermissionNeverAskAgain() {
        toast(R.string.enable_location_access)
        showMyLocationButton.isVisible = false
    }

    private fun onContactPermissionDenied() {
        toast(R.string.location_access_denied)
        showMyLocationButton.isVisible = false
    }

    private fun onContactPermissionShowRationale(permissionRequest: PermissionRequest) {
        permissionRequest.proceed()
    }

    private fun getBitmapFromVectorDrawable(@DrawableRes drawableId: Int, tint: Int): Bitmap? {
        val drawable = AppCompatResources.getDrawable(requireContext(), drawableId)
        drawable?.alpha = 255
        drawable?.setTint(requireContext().getColor(tint))
        return drawable?.toBitmap()
    }
}