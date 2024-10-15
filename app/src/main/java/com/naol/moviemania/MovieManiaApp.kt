package com.naol.moviemania

import android.app.Application
import com.naol.moviemania.data.local.MovieManiaDatabase
import com.naol.moviemania.di.appModule
import com.naol.moviemania.di.databaseModule
import com.naol.moviemania.di.useCasesModule
import com.naol.moviemania.di.viewModelsModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module

class MovieManiaApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MovieManiaApp)
            modules(listOf(appModule, databaseModule, viewModelsModule, useCasesModule))
            androidLogger(Level.DEBUG)
        }
    }
}