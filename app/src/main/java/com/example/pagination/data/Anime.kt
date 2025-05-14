package com.example.pagination.data

data class Anime(
    val mal_id: Int,
    val title_english: String,
    val images: Images,
    val trailer: Trailer,
    val episodes: Int,
    val aired: Aired,
    val rating: String,
    val score: Float,
    val synopsis: String
)
