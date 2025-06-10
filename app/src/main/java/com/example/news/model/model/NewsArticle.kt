package com.example.news.model.model

data class NewsArticle(
    val title: String,
    val urlToImage: String?,
    val description: String?,
    val content: String?,
    val author: String? = null,
    val publishedAt: String = "",
    val url: String?
) {
    val publishedAtRelative: String
        get() = "5 hours ago"
}
