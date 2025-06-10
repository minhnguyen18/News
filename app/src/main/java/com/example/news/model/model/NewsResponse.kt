package com.example.news.model.model

data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<NewsArticle>,

)
