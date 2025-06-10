package com.example.news.model.repository

import com.example.news.model.model.NewsArticle

object NewsRepository {
    fun getNews(): List<NewsArticle> {
        return listOf(
            NewsArticle(
                title = "Ola e-scooters go on sale",
                urlToImage = "https://example.com/scooter.jpg",
                description = "Electric scooters launched with new features",
                content = "Full content of the article goes here...",
                author = "Ola Team",
                publishedAt = "2025-06-05T12:00:00Z",
                url = "https://example.com/scooter-article"
            ),
            NewsArticle(
                title = "Bitcoin, Solana, Cardano surge in market",
                urlToImage = "https://example.com/crypto.jpg",
                description = "Crypto market latest update",
                content = "Bitcoin and other major coins rose today...",
                author = "Crypto Desk",
                publishedAt = "2025-06-05T10:15:00Z",
                url = "https://example.com/scooter-article"
            )
        )
    }
}
