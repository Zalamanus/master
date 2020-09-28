package com.example.permissionsanddate

import android.app.Application
import com.example.permissionsanddate.model.MyLocation
import com.jakewharton.threetenabp.AndroidThreeTen

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }

    companion object {
        var locationsList = listOf<MyLocation>()
    }
}