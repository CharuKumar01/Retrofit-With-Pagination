package com.example.pagination.data.TraceMoe

data class AnimeResult(
    val anilist: Int,
    val filename: String,
    val episode: Int?,
    val similarity: Double,
    val video: String?,
    val title: String?,
    val titleEnglish: String?
)
