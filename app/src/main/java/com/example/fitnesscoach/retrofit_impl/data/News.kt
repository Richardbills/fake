package com.example.fitnesscoach.retrofit_impl.data

data class News(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)