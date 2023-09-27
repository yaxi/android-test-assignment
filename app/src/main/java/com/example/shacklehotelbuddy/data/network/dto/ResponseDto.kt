package com.example.shacklehotelbuddy.data.network.dto

import com.google.gson.annotations.SerializedName

data class ResponseDto(
    @SerializedName("data")
    val data: DataDto
)
