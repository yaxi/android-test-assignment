package com.example.shacklehotelbuddy.data.network.dto

import com.google.gson.annotations.SerializedName

data class ReviewsDto(
    @SerializedName("score")
    val score: Float
)
