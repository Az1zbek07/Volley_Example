package com.example.volleyexamples

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.example.volleyexamples.databinding.ActivityPhotoBinding
import com.example.volleyexamples.model.Photo
import com.example.volleyexamples.util.Constants
import com.example.volleyexamples.util.NetworkUtils
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class PhotoActivity : AppCompatActivity() {
    private val binding by lazy { ActivityPhotoBinding.inflate(layoutInflater) }
    lateinit var requestQueue: RequestQueue
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val networkUtils = NetworkUtils(this)
        if (networkUtils.isNetworkConnected()){
            requestQueue = Volley.newRequestQueue(this)
            getPhoto()
        }else{
            Snackbar.make(binding.root, "No internet connections", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun getPhoto(){
        val id = intent.getIntExtra("id", 1)
        val jsonPhoto = JsonObjectRequest(
            "${Constants.PHOTO_URL}/$id",
            {
                val type: Type = object : TypeToken<Photo>() {}.type
                val photo = Gson().fromJson<Photo>(it.toString(), type)

                with(binding){
                    Glide.with(imageView)
                        .load(photo.image)
                        .into(imageView)
                }
                binding.textTitle.text = photo.title
            },
            {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
        )
        requestQueue.add(jsonPhoto)
    }
}