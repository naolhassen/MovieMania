package com.naol.moviemania.di

import androidx.room.Room
import com.naol.moviemania.data.local.MovieManiaDatabase
import com.naol.moviemania.data.local.MovieManiaDatabase.Companion.DATABASE_NAME
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            get(), MovieManiaDatabase::class.java, DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

}