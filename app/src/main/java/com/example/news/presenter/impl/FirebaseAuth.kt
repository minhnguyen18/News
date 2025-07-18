package com.example.news.presenter.impl

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

// Check if a user exists by email (for pre-check before registration)
fun checkIfUserExists(email: String, callback: (Boolean, String?) -> Unit) {
    val auth = FirebaseAuth.getInstance()
    auth.fetchSignInMethodsForEmail(email)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val signInMethods = task.result?.signInMethods
                callback(!signInMethods.isNullOrEmpty(), null)
            } else {
                callback(false, task.exception?.message)
            }
        }
}



