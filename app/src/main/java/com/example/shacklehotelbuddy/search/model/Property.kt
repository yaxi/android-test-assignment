package com.example.shacklehotelbuddy.search.model

import com.example.shacklehotelbuddy.data.network.dto.NeighborhoodDto
import com.example.shacklehotelbuddy.data.network.dto.PropertyImageDto
import com.google.gson.annotations.SerializedName

data class Property(
    val id: String,
    val name: String,
    val imageUrl: String,
    val neighborhood: String,
    val ratings: Float
)