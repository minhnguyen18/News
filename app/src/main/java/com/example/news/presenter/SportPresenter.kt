package com.example.news.presenter

import com.example.news.model.model.NewsArticle
import com.example.news.model.repository.SportRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

interface SportView {
    fun showSportsNews(articles: List<NewsArticle>)
    fun showError(message: String)
}

interface SportPresenter {
    fun loadSportsNews(apiKey: String)
    fun attachView(view: SportView)
    fun detachView()
}

class SportPresenterImpl(private val repository: SportRepository) : SportPresenter {
    private var view: SportView? = null
    private val presenterScope = CoroutineScope(Dispatchers.Main + Job())

    override fun attachView(view: SportView) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    override fun loadSportsNews(apiKey: String) {
        presenterScope.launch {
            try {
                val articles = repository.fetchSportsNews(apiKey)
                view?.showSportsNews(articles)
            } catch (e: Exception) {
                view?.showError("Failed to load sports news")
            }
        }
    }
}

