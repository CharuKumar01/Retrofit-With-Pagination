package com.example.pagination.data

data class Job(
    val slug: String,
    val companyName: String,
    val title: String,
    val description: String,
    val remote: Boolean,
    val url: String,
    val tags: List<String>,
    val jobTypes: List<String>,
    val location: String,
    val createdAt: Long
)
