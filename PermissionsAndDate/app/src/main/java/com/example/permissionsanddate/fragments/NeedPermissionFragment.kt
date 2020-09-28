package com.example.permissionsanddate.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.permissionsanddate.MainActivity
import com.example.permissionsanddate.R
import com.example.permissionsanddate.utils.OpenFragment
import com.example.permissionsanddate.utils.showToast
import kotlinx.android.synthetic.main.fragment_needpermission.*

class NeedPermissionFragment : Fragment(R.layout.fragment_needpermission) {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        givePermissionButton.setOnClickListener {
            requestLocationPermission()
        }
    }

    private fun requestLocationPermission() {
        requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),MainActivity.LOCATION_PERMISSION_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            MainActivity.LOCATION_PERMISSION_REQUEST_CODE -> {
                if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                    val locationsListFragment = LocationsListFragment()
                    (activity as OpenFragment).openFragment(locationsListFragment)

                } else {
                    showToast(getString(R.string.need_permission),context)
                }
            }
        }
    }


}
