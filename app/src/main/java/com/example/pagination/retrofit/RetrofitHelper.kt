package com.example.pagination.retrofit

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitHelper {

    private const val JIKAN_BASE_URL = "https://api.jikan.moe/v4/"
    private const val TRACE_MOE_BASE_URL = "https://api.trace.moe/"

    @Provides
    @Singleton
    @Named("Jikan")
    fun getJikanInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(JIKAN_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Named("Jikan")
    @Singleton
    fun provideJikanAPI(@Named("Jikan")retrofit: Retrofit): JikanAPI = retrofit.create(JikanAPI::class.java)

    @Provides
    @Singleton
    @Named("TraceMoe")
    fun getTraceMoeInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(TRACE_MOE_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Named("TraceMoe")
    @Singleton
    fun provideTraceMoeAPI(@Named("TraceMoe") retrofit: Retrofit): TraceMoeAPI = retrofit.create(TraceMoeAPI::class.java)
}