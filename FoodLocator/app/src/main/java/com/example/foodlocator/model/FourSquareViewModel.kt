package com.example.foodlocator.model

import android.Manifest
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import androidx.core.app.ActivityCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.foodlocator.MainActivity
import com.example.foodlocator.utils.SingleLiveEvent
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.launch


class FourSquareViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = FourSquareRepository()

    private var locationRequest: LocationRequest = LocationRequest.create()
        .setFastestInterval(1000)
        .setInterval(5000)
        .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
    private var fusedLocationClient: FusedLocationProviderClient = LocationServices
        .getFusedLocationProviderClient(getApplication() as Context)

    private val venuesLiveData = MutableLiveData<List<FourSquareVenue>>()
    val venues: LiveData<List<FourSquareVenue>>
        get() = venuesLiveData

    private val locationLiveData = MutableLiveData<Location>()
    val location: LiveData<Location>
        get() = locationLiveData

    private val locationTurnOnNeedLiveData = SingleLiveEvent<ApiException>()
    val locationTurnOnNeed: LiveData<ApiException>
        get() = locationTurnOnNeedLiveData

    fun getVenues(near: String, section: String) {
        viewModelScope.launch {
            venuesLiveData.postValue(repository.getVenues(near, section))
        }
    }

    fun getLocation() {
        if (ActivityCompat.checkSelfPermission(
                getApplication(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            //todo if permissions had not been granted
        } else {
            val builder = LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)
            val task: Task<LocationSettingsResponse> =
                LocationServices.getSettingsClient(getApplication() as Context)
                    .checkLocationSettings(builder.build())
            task.addOnSuccessListener { response ->
                val states = response.locationSettingsStates
                if (states.isLocationPresent) {
                    //Do something
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
                        // Handle result in onActivityResult()
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


    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            locationResult ?: return
            locationResult.locations.last()?.let {
                locationLiveData.postValue(it)
            }
        }
    }


}