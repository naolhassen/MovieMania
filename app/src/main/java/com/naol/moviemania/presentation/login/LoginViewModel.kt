package com.naol.moviemania.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naol.moviemania.data.auth.GoogleAuth
import com.naol.moviemania.domain.repository.AuthRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val authRepository: AuthRepository) : ViewModel() {
    fun signIn() = viewModelScope.launch {
        authRepository.signIn(null, null)
    }

}