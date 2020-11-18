package com.example.foodlocator

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.foodlocator.utils.toast
import com.google.android.gms.location.LocationSettingsStates

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val states = LocationSettingsStates.fromIntent(data);
        when(requestCode) {
            REQUEST_CHECK_SETTINGS -> {
                when(resultCode) {
                    Activity.RESULT_OK -> {
                        // All required changes were successfully made
                        toast("Пользователь включил определение метоположения")
                    }
                    Activity.RESULT_CANCELED -> {
                        // The user was asked to change settings, but chose not to
                        toast("Пользователь не включил определение метоположения")
                    }
                }
            }
        }
    }

    companion object {
        const val REQUEST_CHECK_SETTINGS = 999
    }
}