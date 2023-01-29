package com.example.volleyexamples.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.volleyexamples.databinding.PostLayoutBinding
import com.example.volleyexamples.model.Post

class PostAdapter: ListAdapter<Post, PostAdapter.VHolder>(DiffCallback()) {
    lateinit var onClick: (id: Int) -> Unit
    private class DiffCallback: DiffUtil.ItemCallback<Post>(){
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHolder {
        return VHolder(PostLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class VHolder(private val binding: PostLayoutBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(post: Post){
            binding.textId.text = post.id.toString()
            binding.textTitle.text = post.title

            itemView.setOnClickListener {
                onClick(post.id)
            }
        }
    }
}