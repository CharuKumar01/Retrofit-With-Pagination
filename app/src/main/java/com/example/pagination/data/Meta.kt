package com.example.pagination.data

data class Meta(
    val currentPage: Int,
    val from: Int,
    val path: String,
    val perPage: Int,
    val to: Int,
    val terms: String,
    val info: String
)
