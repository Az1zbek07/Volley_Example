package com.example.volleyexamples.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.volleyexamples.databinding.PhotoLayoutBinding
import com.example.volleyexamples.model.Photo
import com.google.gson.annotations.Until

class PhotoAdapter: ListAdapter<Photo, PhotoAdapter.VHolder>(DiffCallback()) {
    lateinit var onClick: (id: Int) -> Unit
    private class DiffCallback: DiffUtil.ItemCallback<Photo>(){
        override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHolder {
        return VHolder(PhotoLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class VHolder(private val binding: PhotoLayoutBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(photo: Photo){
            with(binding){
                Glide.with(imageView)
                    .load(photo.image)
                    .into(imageView)
            }
            binding.textTitle.text = photo.title
            itemView.setOnClickListener {
                onClick(photo.id)
            }
        }
    }
}