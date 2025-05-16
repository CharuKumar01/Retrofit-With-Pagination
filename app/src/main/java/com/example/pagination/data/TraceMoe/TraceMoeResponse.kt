package com.example.pagination.data.TraceMoe

data class TraceMoeResponse(
    val frameCount: Int,
    val error: String?,
    val result: List<AnimeResult>
)