package com.example.volleyexamples.model

import com.google.gson.annotations.SerializedName

data class Photo(
    val title: String,
    val id: Int,
    @SerializedName("url")
    val image: String
)
