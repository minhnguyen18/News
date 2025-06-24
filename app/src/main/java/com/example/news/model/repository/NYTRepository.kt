package com.example.news.model.repository

import com.example.news.model.model.NYTArticle
import com.example.news.model.network.NYTApiService  //
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NYTRepository(private val apiService: NYTApiService) {

    suspend fun fetchTopStories(apiKey: String): List<NYTArticle> = withContext(Dispatchers.IO) {
        val response = apiService.getTopStories(apiKey)
        response.results.map {
            NYTArticle(
                title = it.title,
                byline = it.byline,
                publishedDate = it.published_date,
                imageUrl = it.multimedia?.firstOrNull()?.url,
                url = it.url,
                abstract = it.abstract ?: ""
            )
        }
    }
}

