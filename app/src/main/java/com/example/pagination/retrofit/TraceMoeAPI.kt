package com.example.pagination.retrofit

import com.example.pagination.data.TraceMoe.TraceMoeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TraceMoeAPI {

    @GET("/search")
    suspend fun getAnimeDetails(@Query("image") base64Image: String): Response<TraceMoeResponse>
}