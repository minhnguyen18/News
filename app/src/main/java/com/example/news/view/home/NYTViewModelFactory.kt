package com.example.news.view.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.news.presenter.NYTPresenter

class NYTViewModelFactory(
    private val presenter: NYTPresenter,
    private val apiKey: String
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NYTViewModel(presenter, apiKey) as T
    }
}

