package com.example.news.model.model

data class NYTArticle(
    override val title: String,
    val byline: String?,
    val publishedDate: String,
    val imageUrl: String?,
    override val url: String?,
    val abstract: String
) : BaseArticle {
    override val description: String get() = abstract
    override val content: String get() = abstract
    override val urlToImage: String? get() = imageUrl
    override val author: String? get() = byline
    override val publishedAt: String get() = publishedDate
}

