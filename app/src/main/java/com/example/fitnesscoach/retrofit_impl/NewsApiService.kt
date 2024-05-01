package com.example.fitnesscoach.retrofit_impl

import com.example.fitnesscoach.retrofit_impl.data.News
import retrofit2.http.GET

interface NewsApiService {
    @GET("top-headlines?country=gb&category=health&sortBy=publishedAt&apiKey=2ef3b1666cbd412481baa2eeb67b81d9")
    suspend fun getNews() : News
}