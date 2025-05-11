package com.example.pagination.retrofit

import com.example.pagination.data.AnimeResponse
import retrofit2.Response
import retrofit2.http.GET

interface JikanAPI {

    @GET("top/anime")
    suspend fun getTopAnime(): Response<AnimeResponse>
}