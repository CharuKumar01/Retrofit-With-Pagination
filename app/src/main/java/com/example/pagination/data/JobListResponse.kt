package com.example.pagination.data

data class JobListResponse(
    val data: List<Job>,
    val links: Links,
    val meta: Meta
)
