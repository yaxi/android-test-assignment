package com.example.shacklehotelbuddy.data.network

import com.example.shacklehotelbuddy.data.network.dto.ResponseDto
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ShackleService {

    @POST("properties/v2/list")
    suspend fun postSearch(
        @Header("X-RapidAPI-Key") key: String,
        @Header("X-RapidAPI-Host") host: String = "hotels4.p.rapidapi.com",
        @Body postBody: RequestBody
    ): Response<ResponseDto>
}