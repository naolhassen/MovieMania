package com.naol.moviemania

import android.app.Application
import com.naol.moviemania.di.appModule
import com.naol.moviemania.di.databaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MovieManiaApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MovieManiaApp)
            modules(appModule,databaseModule)
        }
    }
}