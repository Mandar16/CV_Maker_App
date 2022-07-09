package com.example.socialme.daos

import com.example.socialme.models.Post
import com.example.socialme.models.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class PostDao {
    val db = FirebaseFirestore.getInstance()
    val postCollection = db.collection("post")
    val auth = Firebase.auth
    fun addPost(text:String){
        val currentUserId = auth.currentUser!!.uid
        GlobalScope.launch(Dispatchers.IO){
            val userdao = UserDao()
            val user = userdao.getUserByid(currentUserId).await().toObject(User::class.java)
            val currentTime = System.currentTimeMillis()
            val post = user?.let { Post(text, it, currentTime) }
            if (post != null) {
                postCollection.document().set(post)
            }

        }

    }
    fun getPostbyId(postId:String): Task<DocumentSnapshot> {
        return postCollection.document(postId).get()
    }


    fun updateLikes(postId:String){
        val currentUserId = auth.currentUser!!.uid
        GlobalScope.launch(Dispatchers.IO){
            val post = getPostbyId(postId).await().toObject(Post::class.java)
            val isLiked = post?.likedBy?.contains(currentUserId)

            if(isLiked == true){
                post.likedBy.remove(currentUserId)
            }else{
                if (post != null) {
                    post.likedBy.add(currentUserId)
                }
            }

            if (post != null) {
                postCollection.document(postId).set(post)
            }

        }

    }
}