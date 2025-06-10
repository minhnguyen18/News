package com.example.news.presenter
import com.example.news.model.model.NewsArticle
interface HomeView {
    fun showNews(articles: List<NewsArticle>)
}

interface NewsPresenter {
    fun loadNews()
    fun attachView(view: HomeView)
    fun detachView()
}