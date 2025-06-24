package com.example.news.model.model

interface BaseArticle {
    val title: String
    val description: String?
    val content: String?
    val urlToImage: String?
    val author: String?
    val publishedAt: String
    val url: String?
}
