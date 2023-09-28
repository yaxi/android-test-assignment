package com.example.shacklehotelbuddy.data.network.dto

import com.google.gson.annotations.SerializedName

data class PropertySearchDto(
    @SerializedName("properties")
    val properties: List<PropertyDto>
)

data class PropertyDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("propertyImage")
    val propertyImage: PropertyImageDto,
    @SerializedName("neighborhood")
    val neighborhood: NeighborhoodDto,
    @SerializedName("price")
    val price: PriceDto,
    @SerializedName("reviews")
    val reviewsDto: ReviewsDto,
)
