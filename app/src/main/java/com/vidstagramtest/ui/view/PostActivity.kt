package com.vidstagramtest.ui.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vidstagramtest.R
import com.vidstagramtest.model.PostModel
import com.vidstagramtest.ui.LoadingState
import com.vidstagramtest.ui.view.adapter.PostAdapter
import com.vidstagramtest.ui.viewmodel.PostViewModel
import kotlinx.android.synthetic.main.post_activity_layout.*


class PostActivity : BaseDaggerActivity() {

    private lateinit var postViewModel: PostViewModel
    private lateinit var postAdapter: PostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        postViewModel = getViewModel(this, PostViewModel::class.java)
        setContentView(R.layout.post_activity_layout)
        setupViews()
        setupRecyclerView()
        initLiveData()
        postViewModel.onCreate()
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.signOut -> {
                postViewModel.signOut()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
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
                }
                is LoadingState.Error -> {
                    showProgressDialog(false)
                }
            }

        }
        postViewModel.loadingState.observe(this, loadingObserver)

        val postListObservable = Observer<List<PostModel>> { postList ->
            postAdapter.setList(postList)
        }
        postViewModel.postLiveData.observe(this, postListObservable)

        val changedPostObserver = Observer<List<PostModel>> { newPosts ->
            postAdapter.addNewItems(newPosts.sortedByDescending { it.timestamp })
        }
        postViewModel.changedPostsLiveData.observe(this, changedPostObserver)
    }

    private fun setupViews() {
        addPostButton.setOnClickListener {
            val intent = Intent(this, NewPostActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        postAdapter = PostAdapter(this)
        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        postList.layoutManager = layoutManager
        postList.adapter = postAdapter
    }

    private fun showProgressDialog(state: Boolean) {
        if (state) {
            progressPost.visibility = View.VISIBLE
        } else {
            progressPost.visibility = View.GONE
        }
    }
}
