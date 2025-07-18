package com.example.news.presenter.impl

import com.example.news.presenter.AuthView
import com.example.news.presenter.AuthPresenter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class AuthPresenterImpl : AuthPresenter {
    private var view: AuthView? = null

    override fun attachView(view: AuthView) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    override fun login(email: String, password: String) {
        val auth = FirebaseAuth.getInstance()
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    view?.onLoginSuccess()
                } else {
                    val errorMessage = task.exception?.localizedMessage ?: "Login failed"
                    view?.onLoginFailure(errorMessage)
                }
            }
    }

    override fun register(email: String, password: String, confirmPassword: String) {
        val auth = FirebaseAuth.getInstance()
        if (password != confirmPassword) {
            view?.onRegisterFailure("Passwords do not match")
            return
        }
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // (Optional) Save user info to Realtime Database
                    val userId = auth.currentUser?.uid ?: ""
                    val userMap = mapOf("email" to email)
                    val db = FirebaseDatabase.getInstance().reference
                    db.child("users").child(userId).setValue(userMap)
                        .addOnSuccessListener {
                            view?.onRegisterSuccess()
                        }
                        .addOnFailureListener { e ->
                            auth.currentUser?.delete()
                            view?.onRegisterFailure("Database error: ${e.message}")
                        }
                } else {
                    val errorMessage = task.exception?.localizedMessage ?: "Register failed"
                    view?.onRegisterFailure(errorMessage)
                }
            }
    }
}
