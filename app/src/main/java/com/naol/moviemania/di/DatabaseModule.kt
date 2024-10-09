package com.naol.moviemania.di

import com.naol.moviemania.data.local.MovieManiaDatabase
import com.naol.moviemania.data.local.provideDatabase
import com.naol.moviemania.data.local.repository.FavoriteMovieRepository
import com.naol.moviemania.domain.usecase.GetFavMoviesUseCase
import com.naol.moviemania.domain.usecase.SaveFavMovieUseCase
import org.koin.dsl.module

val databaseModule = module {
    single { provideDatabase(get()) }
    single { get<MovieManiaDatabase>().movieDao() }
    single { FavoriteMovieRepository(get()) }
    single { SaveFavMovieUseCase(get()) }
    single { GetFavMoviesUseCase(get()) }
}



