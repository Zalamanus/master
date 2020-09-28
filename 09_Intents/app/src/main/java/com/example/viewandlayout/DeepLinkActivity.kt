package com.example.viewandlayout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_deep_link.*

class DeepLinkActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deep_link)

        fillURL(intent.dataString)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        fillURL(intent?.toString())
    }

    private fun fillURL(textToFill: String?) {
        urlTv.text = textToFill
    }
}
