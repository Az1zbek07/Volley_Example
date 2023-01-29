package com.example.volleyexamples

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.volleyexamples.databinding.ActivityPostBinding
import com.example.volleyexamples.model.Post
import com.example.volleyexamples.util.Constants
import com.example.volleyexamples.util.NetworkUtils
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class PostActivity : AppCompatActivity() {
    private val binding by lazy { ActivityPostBinding.inflate(layoutInflater) }
    private lateinit var requestQueue: RequestQueue
    private var id = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        id = intent.getIntExtra("id", 1)
        val networkUtils = NetworkUtils(this)
        if (networkUtils.isNetworkConnected()){
            requestQueue = Volley.newRequestQueue(this)
            bindPost()
        }else{
            Snackbar.make(binding.root, "No internet connections", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun bindPost(){
        val jsonPost = JsonObjectRequest(
            "${Constants.POST_URL}/$id",
            {
                val type: Type = object : TypeToken<Post>() {}.type
                val post = Gson().fromJson<Post>(it.toString(), type)
                binding.textTitle.text = post.title
                binding.textBody.text = post.body
            },
            {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
        )
        requestQueue.add(jsonPost)
    }
}