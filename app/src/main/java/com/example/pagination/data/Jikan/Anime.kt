package com.example.pagination.data.Jikan

data class Anime(
    val mal_id: Int,
    val title_english: String? = null,
    val images: Images,
    val trailer: Trailer,
    val episodes: Int,
    val aired: Aired,
    val rating: String,
    val score: Float,
//    val synopsis: String
)
