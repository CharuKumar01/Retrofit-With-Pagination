package com.example.pagination.retrofit

import com.example.pagination.data.AnimeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface JikanAPI {

    @GET("top/anime")
    suspend fun getTopAnime(@Query("page") page: Int): Response<AnimeResponse>
}