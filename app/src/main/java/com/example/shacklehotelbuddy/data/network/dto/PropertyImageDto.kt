package com.example.shacklehotelbuddy.data.network.dto

import com.google.gson.annotations.SerializedName

data class PropertyImageDto(
    @SerializedName("image")
    val image: ImageDto
)

data class ImageDto(
    @SerializedName("url")
    val url: String
)
