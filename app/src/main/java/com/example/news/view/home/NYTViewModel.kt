package com.example.news.view.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news.model.model.NYTArticle
import com.example.news.presenter.NYTPresenter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NYTViewModel(private val presenter: NYTPresenter, private val apiKey: String) : ViewModel() {

    private val _articles = MutableStateFlow<List<NYTArticle>>(emptyList())
    val articles: StateFlow<List<NYTArticle>> = _articles

    private val _selectedArticle = MutableStateFlow<NYTArticle?>(null)
    val selectedArticle: StateFlow<NYTArticle?> = _selectedArticle

    fun selectArticle(article: NYTArticle) {
        println("DEBUG: setSelectedArticle -> ${article.title}")
        _selectedArticle.value = article
    }

    init {
        viewModelScope.launch {
            _articles.value = presenter.loadArticles(apiKey)
        }
    }
}


