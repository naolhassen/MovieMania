package com.naol.moviemania.data.auth

import com.google.firebase.auth.FirebaseAuth
import com.naol.moviemania.domain.repository.AuthRepository

class EmailAuth(private val firebaseAuth: FirebaseAuth) : AuthRepository {
    override suspend fun signIn(email: String?, password: String?): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun signUp(email: String?, password: String?) {
        TODO("Not yet implemented")
    }

    override suspend fun signOut() {
        TODO("Not yet implemented")
    }

    override suspend fun isSignedIn(): Boolean {
        TODO("Not yet implemented")
    }
}