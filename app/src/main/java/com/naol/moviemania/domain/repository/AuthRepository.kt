package com.naol.moviemania.domain.repository

interface AuthRepository {
    suspend fun signIn(email: String?, password: String?): Boolean
    suspend fun signUp(email: String?, password: String?) { }
    suspend fun signOut()
    suspend fun isSignedIn(): Boolean
}