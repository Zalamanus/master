package com.example.foodlocator.model

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.foodlocator.R
import com.example.foodlocator.utils.SingleLiveEvent
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.launch


class FourSquareViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = FourSquareRepository()
    private val sharedPrefs =
        application.getSharedPreferences(SHARED_PREFS_FILE, Context.MODE_PRIVATE)

    private var defaultSection = sharedPrefs.getString(DEFAULT_SECTION, "food").toString()

    private var locationRequest: LocationRequest = LocationRequest.create()
        .setFastestInterval(5000)
        .setInterval(10000)
        .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
    private var fusedLocationClient: FusedLocationProviderClient = LocationServices
        .getFusedLocationProviderClient(getApplication() as Context)

    private val venueMarkersLiveData = MutableLiveData<List<CustomMarker>>()
    val venueMarkers: LiveData<List<CustomMarker>>
        get() = venueMarkersLiveData

    private val userMarkerLiveData = MutableLiveData<CustomMarker>()
    val userMarker: LiveData<CustomMarker>
        get() = userMarkerLiveData

    private val locationTurnOnNeedLiveData = SingleLiveEvent<ApiException>()
    val locationTurnOnNeed: LiveData<ApiException>
        get() = locationTurnOnNeedLiveData

    fun getVenues(ll: String, section: String, radius: Int?) {
        viewModelScope.launch {
            val markerList = mutableListOf<CustomMarker>()
            repository.getVenues(ll, section, radius).forEach { venue ->
                val drawable = when (section) {
                    FOOD_SECTION -> R.drawable.ic_baseline_food_bank
                    COFFEE_SECTION -> R.drawable.ic_baseline_emoji_food_beverage
                    SHOPS_SECTION -> R.drawable.ic_baseline_shopping_cart
                    TRENDING_SECTION -> R.drawable.ic_baseline_star
                    else -> R.drawable.ic_baseline_not_listed_location
                }
                val tint = when(section) {
                    FOOD_SECTION -> R.color.food
                    COFFEE_SECTION -> R.color.coffee
                    SHOPS_SECTION -> R.color.shops
                    TRENDING_SECTION -> R.color.trending
                    else -> R.color.white
                }
                val marker = CustomMarker.Builder()
                    .latitude(venue.location.lat)
                    .longitude(venue.location.lng)
                    .title(venue.name)
                    .drawableRes(drawable)
                    .tint(tint)
                    .build()
                markerList.add(marker)
            }
            venueMarkersLiveData.postValue(markerList)
        }


        setDefaultSection(section)
    }

    fun getCoarseLocation() {
        if (ActivityCompat.checkSelfPermission(
                getApplication(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            location?.let {
                val marker = getUserCustomMarker(location)
                userMarkerLiveData.postValue(marker)
            }
        }
    }

    private fun getUserCustomMarker(location: Location): CustomMarker {
        return CustomMarker.Builder()
            .latitude(location.latitude)
            .longitude(location.longitude)
            .title((getApplication() as Context).getString(R.string.me))
            .drawableRes(R.drawable.ic_baseline_person_pin_circle)
            .tint(R.color.purple_500)
            .build()
    }

    fun getFineLocation() {
        if (ActivityCompat.checkSelfPermission(
                getApplication(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val builder = LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)
            val task: Task<LocationSettingsResponse> =
                LocationServices.getSettingsClient(getApplication() as Context)
                    .checkLocationSettings(builder.build())
            task.addOnSuccessListener { response ->
                val states = response.locationSettingsStates
                if (states.isLocationPresent) {
                    //Do something when location is enabled
                    fusedLocationClient.requestLocationUpdates(
                        locationRequest,
                        locationCallback,
                        Looper.getMainLooper()
                    )
                }
            }
            task.addOnFailureListener { e ->
                if (e is ResolvableApiException) {
                    try {
                        locationTurnOnNeedLiveData.postValue(e)
                    } catch (sendEx: IntentSender.SendIntentException) {
                    }
                }
            }
        }
    }

    fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    fun getDefaultSection() = defaultSection

    private fun setDefaultSection(section: String) {
        defaultSection = section
        sharedPrefs.edit().putString(DEFAULT_SECTION, defaultSection).apply()
    }


    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            locationResult ?: return
            locationResult.locations.last()?.let {
                userMarkerLiveData.postValue(getUserCustomMarker(it))
            }
        }
    }

    companion object {
        private const val SHARED_PREFS_FILE = "prefs"
        private const val DEFAULT_SECTION = "default_section"
        const val FOOD_SECTION = "food"
        const val SHOPS_SECTION = "shops"
        const val COFFEE_SECTION = "coffee"
        const val TRENDING_SECTION = "trending"
    }

}