package com.naol.moviemania.domain.repository

interface Auth {
    suspend fun signIn(email: String, password: String): String
    suspend fun signUp(email: String, password: String): String
    suspend fun signOut()
    suspend fun isSignedIn(): Boolean

}