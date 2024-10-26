package com.naol.moviemania.di

import android.app.Activity
import androidx.credentials.CredentialManager
import com.google.firebase.auth.FirebaseAuth
import com.naol.moviemania.data.auth.EmailAuth
import com.naol.moviemania.data.auth.GoogleAuth
import com.naol.moviemania.domain.repository.AuthRepository
import com.naol.moviemania.presentation.login.LoginViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

val authModule = module {
    single {
        CredentialManager.create(get())
    }

    single {
        FirebaseAuth.getInstance()
    }

    single<AuthRepository>(qualifier = named("googleAuth")) {
        GoogleAuth(get(), get(), get())
    }

    // Inject GoogleAuth with an Activity context when needed
    factory<AuthRepository> { (activity: Activity) ->
        GoogleAuth(get(), get(), activity)
    }

    // Inject LoginViewModel with GoogleAuth as the implementation of AuthRepository
    viewModel { (activity: Activity) ->
        LoginViewModel(get { parametersOf(activity) })
    }
}