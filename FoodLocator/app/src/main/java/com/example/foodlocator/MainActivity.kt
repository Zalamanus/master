package com.example.foodlocator

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import com.example.foodlocator.model.FourSquareViewModel
import com.example.foodlocator.utils.toast
import com.google.android.gms.location.LocationSettingsStates
import kotlinx.android.synthetic.main.fragment_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            REQUEST_CHECK_SETTINGS -> {
                when(resultCode) {
                    Activity.RESULT_OK -> {
                        // All required changes were successfully made
                        toast(getString(R.string.wait_for_location_update))
                        showMyLocationButton.callOnClick()
                    }
                    Activity.RESULT_CANCELED -> {
                        // The user was asked to change settings, but chose not to
                        toast(getString(R.string.location_determining_imposible))
                    }
                }
            }
        }
    }

    companion object {
        const val REQUEST_CHECK_SETTINGS = 999
    }
}