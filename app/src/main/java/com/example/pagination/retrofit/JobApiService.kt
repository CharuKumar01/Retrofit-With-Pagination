package com.example.pagination.retrofit
import com.example.pagination.data.JobListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface JobApiService {

    @GET("job-board-api")
    fun getJobList(
        @Query("page") page: Int
    ): Call<JobListResponse>
}