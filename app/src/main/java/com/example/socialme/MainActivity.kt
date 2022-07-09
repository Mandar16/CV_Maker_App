package com.example.socialme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.socialme.daos.PostDao
import com.example.socialme.models.Post
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), IpostAdapter {
    private lateinit var postAdapter: PostAdapter
    private lateinit var postDao : PostDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        floatingActionButton.setOnClickListener{
            val intent = Intent(this,CreatePost::class.java)
        }
        setupRecyclerView()

    }

    private fun setupRecyclerView() {
        val postCollection = postDao.postCollection
        val query = postCollection.orderBy("createdAt", Query.Direction.DESCENDING)
        val option = FirestoreRecyclerOptions.Builder<Post>().setQuery(query,Post::class.java).build()
         postAdapter = PostAdapter(option,this)
        postRecycler.adapter= postAdapter
        postRecycler.layoutManager = LinearLayoutManager(this)
    }

    override fun onStart() {
        super.onStart()
        postAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        postAdapter.stopListening()
    }

    override fun onlikeClicked(postId: String) {
        postDao.updateLikes(postId)
    }
}