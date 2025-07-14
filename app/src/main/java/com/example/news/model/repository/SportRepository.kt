package com.example.news.model.repository

import com.example.news.model.model.NewsArticle
import com.example.news.model.network.NewsApiService

class SportRepository(private val apiService: NewsApiService) {
    suspend fun fetchSportsNews(apiKey: String): List<NewsArticle> {
        return try {
            apiService.getSportsNews(apiKey = apiKey).articles
        } catch (e: Exception) {
            emptyList()
        }
    }
}

