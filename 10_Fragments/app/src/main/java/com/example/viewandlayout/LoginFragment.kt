package com.example.viewandlayout

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment(R.layout.fragment_login) {
    lateinit var myLifecycleObserver: MyLifecycleObserver
    private var formState: FormState? = null
    var loginExist = false
    var passwordExist = false
    var codedProgressBar: ProgressBar? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
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
                    showMainFragment()
                } else {
                    formState = FormState(false, getString(R.string.autorization_failure))
                    avtorizationStatusTextview.text = formState.toString()
                }
            }, 2000)
        }

        makeANRButton.setOnClickListener {
            Thread.sleep(10000)
        }


    }

    private fun showMainFragment() {
        val mainFragment = MainFragment()
        fragmentManager?.beginTransaction()!!
            .replace(R.id.frame_container, mainFragment)
            .setPrimaryNavigationFragment(mainFragment)
            .commit()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("state", formState)
        Log.d(MyLifecycleObserver.TAG, "State is saved")
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        formState = savedInstanceState?.getParcelable("state")
        avtorizationStatusTextview.text = formState?.toString()

        showLoginButton()
    }


    private fun showProgress() {
        if (codedProgressBar == null) {
            codedProgressBar = ProgressBar(context).apply {
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