package com.example.news.presenter

import com.example.news.model.model.NYTArticle
import com.example.news.model.repository.NYTRepository

class NYTPresenter(private val repository: NYTRepository) {
    suspend fun loadArticles(apiKey: String): List<NYTArticle> {
        return try {
            repository.fetchTopStories(apiKey)
        } catch (e: Exception) {
            emptyList()
        }
    }
}
