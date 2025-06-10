package com.example.news.presenter.impl

import com.example.news.model.repository.AuthRepository
import com.example.news.presenter.AuthView
import com.example.news.presenter.AuthPresenter

class AuthPresenterImpl: AuthPresenter {
    private var view: AuthView? = null

    override fun attachView(view: AuthView) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    override fun login(email: String, password: String) {
        if (AuthRepository.login(email, password)) view?.onLoginSuccess()
        else view?.onLoginFailure("Login failed")
    }

    override fun register(email: String, password: String, confirmPassword: String) {
        if (AuthRepository.register(email, password, confirmPassword)) view?.onRegisterSuccess()
        else view?.onRegisterFailure("Register failed")
    }
}