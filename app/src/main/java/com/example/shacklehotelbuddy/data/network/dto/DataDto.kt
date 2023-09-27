package com.example.shacklehotelbuddy.data.network.dto

import com.google.gson.annotations.SerializedName

data class DataDto(
    @SerializedName("propertySearch")
    val propertySearchDto: PropertySearchDto
)
