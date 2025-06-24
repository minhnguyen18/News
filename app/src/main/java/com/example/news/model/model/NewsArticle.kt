package com.example.news.model.model

data class NewsArticle(
    override val title: String,
    override val urlToImage: String?,
    override val description: String?,
    override val content: String?,
    override val author: String? = null,
    override val publishedAt: String = "",
    override val url: String?
) : BaseArticle {
    val publishedAtRelative: String
        get() = "5 hours ago"
}