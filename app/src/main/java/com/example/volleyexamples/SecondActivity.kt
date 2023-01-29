package com.example.volleyexamples

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Network
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.volleyexamples.adapter.PhotoAdapter
import com.example.volleyexamples.databinding.ActivitySecondBinding
import com.example.volleyexamples.model.Photo
import com.example.volleyexamples.util.Constants
import com.example.volleyexamples.util.NetworkUtils
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class SecondActivity : AppCompatActivity() {
    private val binding by lazy { ActivitySecondBinding.inflate(layoutInflater) }
    private val photoAdapter by lazy { PhotoAdapter() }
    private lateinit var requestQueue: RequestQueue
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val networkUtils = NetworkUtils(this)
        if (networkUtils.isNetworkConnected()){
            requestQueue = Volley.newRequestQueue(this)
            getAllPhotos()
        }else{
            Snackbar.make(binding.root, "No internet connections", Snackbar.LENGTH_SHORT).show()
        }

        binding.rv.apply {
            adapter = photoAdapter
            layoutManager = LinearLayoutManager(this@SecondActivity)
        }
        photoAdapter.onClick = {
            val intent = Intent(this, PhotoActivity::class.java)
            intent.putExtra("id", it)
            startActivity(intent)
        }
    }

    private fun getAllPhotos(){
        val jsonPhotos = JsonArrayRequest(
            Constants.PHOTO_URL,
            {
                val type: Type = object : TypeToken<List<Photo>>() {}.type
                val photoList = Gson().fromJson<List<Photo>>(it.toString(), type)
                photoAdapter.submitList(photoList)
            },
            {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
        )
        requestQueue.add(jsonPhotos)
    }
}