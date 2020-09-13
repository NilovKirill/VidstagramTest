package com.vidstagramtest.ui.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.vidstagramtest.R
import com.vidstagramtest.ui.LoadingState
import com.vidstagramtest.ui.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.login_activity_layout.*

class LoginActivity : BaseDaggerActivity() {

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginViewModel = getViewModel(this, LoginViewModel::class.java)
        setContentView(R.layout.login_activity_layout)
        setupView()
        initLiveData()
    }

    override fun onStart() {
        super.onStart()
        loginViewModel.onStart()
    }

    private fun initLiveData() {
        val loadingObserver = Observer<LoadingState> { value ->
            when (value) {
                is LoadingState.Loading -> {
                    showProgressDialog(true)
                }
                is LoadingState.Done -> {
                    //Sign in done
                    showProgressDialog(false)
                    val intent = Intent(this, PostActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                is LoadingState.Error -> {
                    showProgressDialog(false)
                }
            }
        }
        loginViewModel.loadingState.observe(this, loadingObserver)
    }

    private fun setupView() {
        createNewUserButton.setOnClickListener {
            val intent = Intent(this, NewUserActivity::class.java)
            startActivity(intent)
        }
        signInButton.setOnClickListener {
            loginViewModel.signIn(
                emailET.text.toString(),
                passwordET.text.toString()
            )
        }
    }

    private fun showProgressDialog(state: Boolean) {
        if (state) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }
}
