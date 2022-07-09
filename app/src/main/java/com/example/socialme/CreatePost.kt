package com.example.socialme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.socialme.daos.PostDao
import kotlinx.android.synthetic.main.activity_create_post.*

class CreatePost : AppCompatActivity() {
    private lateinit var postDao: PostDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post)
        post_button.setOnClickListener {
            val input = createpost_editText.text.toString().trim()
            if(input.isNotEmpty()){
                postDao.addPost(input)
            }
        }
    }
}