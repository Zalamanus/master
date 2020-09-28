package com.example.viewandlayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ProgressBar
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*

const val IMAGE_URL = "https://hackernoon.com/drafts/h7yw28l8.png"

class MainActivity : AppCompatActivity() {
    var loginExist = false
    var passwordExist = false
    var codedProgressBar: ProgressBar? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


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
        }
    }

    override fun onResume() {
        super.onResume()
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
            Toast.makeText(this, R.string.autorization_accepted, Toast.LENGTH_SHORT).show()
            groupToHide.isEnabled = true
        }, 2000)

    }

    private fun showLoginButton() {
        buttonLogin.isEnabled = loginExist && passwordExist && cbAcceptAgreement.isChecked
    }

}
