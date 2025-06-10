package com.example.news.presenter

interface AuthView {
    fun onLoginSuccess()
    fun onLoginFailure(error: String)
    fun onRegisterSuccess()
    fun onRegisterFailure(error: String)
}

interface AuthPresenter {
    fun login(email: String, password: String)
    fun register(email: String, password: String, confirmPassword: String)
    fun attachView(view: AuthView)
    fun detachView()
}
