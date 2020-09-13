package com.vidstagramtest.ui.view

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import com.vidstagramtest.R
import com.vidstagramtest.ui.LoadingState
import com.vidstagramtest.ui.viewmodel.NewUserViewModel
import kotlinx.android.synthetic.main.create_new_user_layout.*

class NewUserActivity : BaseDaggerActivity() {

    private lateinit var newUserViewModel: NewUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newUserViewModel = getViewModel(this, NewUserViewModel::class.java)
        setContentView(R.layout.create_new_user_layout)
        setupViews()
        initLiveData()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initLiveData() {
        val loadingObserver = Observer<LoadingState> { value ->
            when (value) {
                is LoadingState.Loading -> {
                    showProgressDialog(true)
                }
                is LoadingState.Done -> {
                    showProgressDialog(false)
                    finish()
                }
                is LoadingState.Error -> {
                    showProgressDialog(false)
                }
            }
        }
        newUserViewModel.loadingState.observe(this, loadingObserver)
    }

    private fun setupViews() {
        supportActionBar?.title = getString(R.string.login_create_new_title)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        createButton.setOnClickListener {
            newUserViewModel.onCreateButtonClick(
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
