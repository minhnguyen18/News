package com.example.news.presenter.impl

import com.example.news.model.repository.NewsRepository
import com.example.news.presenter.NewsPresenter
import com.example.news.presenter.HomeView

class NewsPresenterImpl : NewsPresenter {
    private var view: HomeView? = null

    override fun attachView(view: HomeView) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    override fun loadNews() {
        val articles = NewsRepository.getNews()
        view?.showNews(articles)
    }
}