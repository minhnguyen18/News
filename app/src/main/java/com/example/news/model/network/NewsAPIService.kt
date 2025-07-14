package com.example.news.model.network

import com.example.news.model.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String = "us",
        @Query("apiKey") apiKey: String
    ): NewsResponse

    @GET("top-headlines")
    suspend fun getSportsNews(
        @Query("category") category: String = "sports",
        @Query("country") country: String = "us",
        @Query("apiKey") apiKey: String
    ): NewsResponse
}

