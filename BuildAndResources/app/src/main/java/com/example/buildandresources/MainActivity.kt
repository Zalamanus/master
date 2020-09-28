package com.example.buildandresources

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView = findViewById<TextView>(R.id.textView)
        textView.text = """
            buildType=${BuildConfig.BUILD_TYPE}
            flavor=${BuildConfig.FLAVOR}
            versionCode=${BuildConfig.VERSION_CODE}
            versionName=${BuildConfig.VERSION_NAME}
            applicationId=${BuildConfig.APPLICATION_ID}
        """.trimIndent()
    }
}
