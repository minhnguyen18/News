package com.example.news.model.repository


object AuthRepository {
    fun login(email: String, password: String): Boolean {
        return email == "user@example.com" && password == "password123"
    }

    fun register(email: String, password: String, confirmPassword: String): Boolean {
        return password == confirmPassword && email.contains("@")
    }
}
