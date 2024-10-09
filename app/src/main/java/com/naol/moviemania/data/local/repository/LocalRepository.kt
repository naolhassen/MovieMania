package com.naol.moviemania.data.local.repository

import com.naol.moviemania.data.local.dao.MovieDao
import com.naol.moviemania.data.local.model.toDomain
import com.naol.moviemania.domain.model.Movie
import com.naol.moviemania.domain.model.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map


class LocalRepository(private val movieDao: MovieDao) {
    suspend fun insertMovie(movie: Movie) {
        movieDao.insert(movie.toEntity())
    }

    suspend fun deleteMovie(movie: Movie) {
        movieDao.delete(movie.toEntity())
    }

    suspend fun getAllMovies(): List<Movie> =
        movieDao.getAllMovies().map { result -> result.toDomain() }


    suspend fun deleteAllMovies() {
        movieDao.deleteAll()
    }
}