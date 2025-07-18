package com.example.news.presenter.impl

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


fun registerUser(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseDatabase.getInstance().reference

    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Store additional user info in database
                val userId = auth.currentUser?.uid ?: ""
                val userMap = mapOf(
                    "email" to email
                    // Add other fields here
                )
                db.child("users").child(userId).setValue(userMap)
                    .addOnSuccessListener {
                        onResult(true, null)
                    }
                    .addOnFailureListener { e ->
                        onResult(false, e.message)
                    }
            } else {
                onResult(false, task.exception?.message)
            }
        }
}

