package com.example.news.view.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.example.news.model.model.NewsArticle
import com.example.news.model.network.NewsApiClient
import android.util.Log

class NewsViewModel : ViewModel() {
    var articles = mutableStateOf<List<NewsArticle>>(emptyList())
        private set
    var selectedArticle = mutableStateOf<NewsArticle?>(null)
        private set

    fun selectArticle(article: NewsArticle) {
        selectedArticle.value = article
    }
    init {
        viewModelScope.launch {
            try {
                val response = NewsApiClient.api.getTopHeadlines(
                    country = "us",
                    apiKey = "7d760c25a4b74092a053a8262f2b48a4"
                )
                articles.value = response.articles
            } catch (e: Exception) {
                Log.e("NewsViewModel", "Error: ${e.localizedMessage}", e)
            }
        }
    }
}
