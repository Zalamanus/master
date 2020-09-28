package com.example.viewandlayout

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.ProgressBar
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*

const val IMAGE_URL = "https://hackernoon.com/drafts/h7yw28l8.png"

class MainActivity : AppCompatActivity() {
    lateinit var myLifecycleObserver: MyLifecycleObserver
    lateinit var formState: FormState
    var loginExist = false
    var passwordExist = false
    var codedProgressBar: ProgressBar? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        myLifecycleObserver = MyLifecycleObserver()
        lifecycle.addObserver(myLifecycleObserver)

        Picasso.get().load(IMAGE_URL).into(imageView)

        etLogin.addTextChangedListener { text ->
            loginExist = text.toString().isNotEmpty()
            showLoginButton()
        }
        etPassword.addTextChangedListener { text ->
            passwordExist = text.toString().isNotEmpty()
            showLoginButton()
        }
        cbAcceptAgreement.setOnClickListener {
            showLoginButton()
        }
        buttonLogin.setOnClickListener {
            showProgress()
            Handler().postDelayed({
                if (etLogin.text.toString().equals("login") &&
                    etPassword.text.toString().equals("password")
                ) {
                    formState = FormState(true,getString(R.string.autorization_accepted))
                } else {
                    formState = FormState(false,getString(R.string.autorization_failure))
                }

                avtorizationStatusTextview.text = formState.toString()
            }, 2000)
        }

        makeANRButton.setOnClickListener {
            Thread.sleep(10000)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("state", formState)
        Log.d(MyLifecycleObserver.TAG, "State is saved")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        formState = savedInstanceState.getParcelable<FormState>("state")!!
        avtorizationStatusTextview.text = formState.toString()

        Log.d(MyLifecycleObserver.TAG, "State is restored")
        showLoginButton()
    }


    private fun showProgress() {
        if (codedProgressBar == null) {
            codedProgressBar = ProgressBar(this).apply {
                layoutParams = ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.WRAP_CONTENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
                    startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                    topToBottom = R.id.guideline3
                }
            }
        }
        container.addView(codedProgressBar)
        groupToHide.isEnabled = false

        Handler().postDelayed({
            container.removeView(codedProgressBar)
            groupToHide.isEnabled = true
        }, 2000)

    }

    private fun showLoginButton() {
        buttonLogin.isEnabled = loginExist && passwordExist && cbAcceptAgreement.isChecked
    }

}
