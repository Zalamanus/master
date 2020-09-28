package com.example.viewandlayout

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.invoke
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import kotlinx.android.synthetic.main.activity_login_ok.*

class LoginOkActivity : AppCompatActivity() {

    private var phoneNumberCorrect = false;
    private val dialLauncher = prepareCall(ActivityResultContracts.Dial()) { result ->
        Toast.makeText(this, "Результат операции: ${result.toString()}", Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_ok)

        phoneNumberEt.addTextChangedListener { text ->
            phoneNumberCorrect = Patterns.PHONE.matcher(text).matches()
            enableMakeCallButton()
        }

        makeCallButton.setOnClickListener {
            dialLauncher(phoneNumberEt.text.toString())
        }
    }

    private fun enableMakeCallButton() {
        makeCallButton.isEnabled = phoneNumberCorrect
    }
}
