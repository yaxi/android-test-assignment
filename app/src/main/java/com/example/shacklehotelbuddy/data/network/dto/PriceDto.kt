package com.example.shacklehotelbuddy.data.network.dto

import com.google.gson.annotations.SerializedName

data class PriceDto(
    @SerializedName("lead")
    val lead: LeadDto
)

data class LeadDto(
    @SerializedName("formatted")
    val formatted: String
)
