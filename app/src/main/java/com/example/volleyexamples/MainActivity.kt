package com.example.volleyexamples

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.MessageQueue
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.volleyexamples.adapter.PostAdapter
import com.example.volleyexamples.databinding.ActivityMainBinding
import com.example.volleyexamples.model.Post
import com.example.volleyexamples.util.Constants
import com.example.volleyexamples.util.NetworkUtils
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val postAdapter by lazy { PostAdapter() }
    private lateinit var requestQueue: RequestQueue
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.rv.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = postAdapter
        }

        val networkUtils = NetworkUtils(this)
        if (networkUtils.isNetworkConnected()){
            requestQueue = Volley.newRequestQueue(this)
            getAllPosts()
        }else{
            Snackbar.make(binding.root, "No internet connections", Snackbar.LENGTH_SHORT).show()
        }

        postAdapter.onClick = {
            val intent = Intent(this, PostActivity::class.java)
            intent.putExtra("id", it)
            startActivity(intent)
        }

        binding.btnGo.setOnClickListener {
            startActivity(Intent(this, SecondActivity::class.java))
        }
    }

    private fun getAllPosts(){
        val jsonPosts = JsonArrayRequest(
            Constants.POST_URL,
            {
                val type: Type = object : TypeToken<List<Post>>() {}.type
                val postList = Gson().fromJson<List<Post>>(it.toString(), type)
                postAdapter.submitList(postList)
            },
            {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
        )
        requestQueue.add(jsonPosts)
    }
}