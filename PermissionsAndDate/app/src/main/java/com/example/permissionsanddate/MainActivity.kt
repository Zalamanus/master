package com.example.permissionsanddate

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.permissionsanddate.fragments.LocationsListFragment
import com.example.permissionsanddate.fragments.NeedPermissionFragment
import com.example.permissionsanddate.utils.OpenFragment
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity : AppCompatActivity(), OpenFragment {
    companion object {
        const val LOCATION_PERMISSION_REQUEST_CODE = 123
        const val REQUESTING_LOCATION_UPDATES_KEY = "1234"
        var requestingLocationUpdates = false
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        updateValuesFromBundle(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (!isGmsAvailable(this)) {
            MaterialAlertDialogBuilder(this)
                .setMessage(getString(R.string.needGms))
                .setCancelable(false)
                .setPositiveButton(R.string.ok) {
                    _,_ -> finish()
                }
                .create()
                .show()
        }

        if (savedInstanceState == null) {
            checkPermission()
        }

    }

    private fun checkPermission() {
        val isLocationPermissionGranted = ActivityCompat
            .checkSelfPermission(
                applicationContext, Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        if (isLocationPermissionGranted) {
            val locationsListFragment = LocationsListFragment()
            openFragment(locationsListFragment)

        } else {
            val needPermissionFragment = NeedPermissionFragment()
            openFragment(needPermissionFragment)
        }
    }

    override fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(
            R.id.mainContainer,
            fragment
        )
            .commit()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean(
            REQUESTING_LOCATION_UPDATES_KEY,
            requestingLocationUpdates
        )
        super.onSaveInstanceState(outState)
    }

    private fun updateValuesFromBundle(savedInstanceState: Bundle?) {
        savedInstanceState ?: return
        // Update the value of requestingLocationUpdates from the Bundle.
        if (savedInstanceState.keySet().contains(REQUESTING_LOCATION_UPDATES_KEY)) {
            requestingLocationUpdates = savedInstanceState.getBoolean(
                REQUESTING_LOCATION_UPDATES_KEY)
        }

    }

    private fun isGmsAvailable(context: Context): Boolean {
        val result =
            GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context)
        return ConnectionResult.SUCCESS == result
    }

}