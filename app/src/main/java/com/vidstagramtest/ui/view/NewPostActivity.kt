package com.vidstagramtest.ui.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import com.vidstagramtest.R
import com.vidstagramtest.ui.LoadingState
import com.vidstagramtest.ui.viewmodel.NewPostViewModel
import com.vidstagramtest.utils.GET_FILE_RESULT_CODE
import kotlinx.android.synthetic.main.new_post_layout.*


class NewPostActivity : BaseDaggerActivity() {

    private lateinit var newPostViewModel: NewPostViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newPostViewModel = getViewModel(this, NewPostViewModel::class.java)
        setContentView(R.layout.new_post_layout)
        setupViews()
        initLiveData()
    }


    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GET_FILE_RESULT_CODE && resultCode == Activity.RESULT_OK && data != null) {
            val fileUriString = data.data
            fileUriString?.let {
                newPostViewModel.saveFileUrl(fileUriString)
                fileName.text = newPostViewModel.getFileName(fileUriString, this)
            }
        }
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
        newPostViewModel.loadingState.observe(this, loadingObserver)
    }

    private fun setupViews() {
        supportActionBar?.title = getString(R.string.new_post_title)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        addFileButton.setOnClickListener {
            openFileChooser()
        }

        postButton.setOnClickListener {
            newPostViewModel.addNewPost(postTitle.text.toString())
        }
    }

    private fun openFileChooser() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "video/*"
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        startActivityForResult(intent, GET_FILE_RESULT_CODE)
    }

    private fun showProgressDialog(state: Boolean) {
        if (state) {
            progressPost.visibility = View.VISIBLE
        } else {
            progressPost.visibility = View.GONE
        }
    }
}