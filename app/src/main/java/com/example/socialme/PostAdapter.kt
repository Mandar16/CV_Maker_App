package com.example.socialme


import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.socialme.models.Post
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.ThrowOnExtraProperties

class PostAdapter(options: FirestoreRecyclerOptions<Post> , val listner : IpostAdapter) : FirestoreRecyclerAdapter<Post, PostAdapter.PostViewHolder>(
    options
) {
    class PostViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val postText : TextView = itemView.findViewById(R.id.postTitle)
        val userName : TextView = itemView.findViewById(R.id.userName)
        val imageUrl : ImageView = itemView.findViewById(R.id.userImage)
        val likeButton : ImageButton = itemView.findViewById(R.id.likeButton)
        val createdAt : TextView = itemView.findViewById(R.id.createdAt)
        val likeCount : TextView = itemView.findViewById(R.id.likeCount)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {

        val viewHolder = PostViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.post_item,parent,false))
        viewHolder.likeButton.setOnClickListener {
            listner.onlikeClicked(snapshots.getSnapshot(viewHolder.adapterPosition).id)
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int, model: Post) {
        holder.postText.text = model.text
        holder.userName.text = model.createdBy.displayName
        holder.createdAt.text = Utils.getTimeAgo(model.createdAt)
        Glide.with(holder.imageUrl).load(model.createdBy.Imageurl).circleCrop().into(holder.imageUrl)
        holder.likeCount.text = model.likedBy.size.toString()
    }
}
interface IpostAdapter{
    fun onlikeClicked(postId:String)
}