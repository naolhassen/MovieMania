package com.naol.moviemania.domain.mapper

import com.naol.moviemania.data.local.model.MovieEntity
import com.naol.moviemania.data.remote.model.ApiMovie
import com.naol.moviemania.data.remote.model.ApiMovieDetails
import com.naol.moviemania.domain.model.Movie
import com.naol.moviemania.domain.model.MovieDetails
import com.naol.moviemania.domain.util.toDate

fun ApiMovie.toMovie(): Movie {
    return Movie(
        id = id,
        title = title,
        overview = overview,
        posterPath = poster_path,
        releaseDate = release_date.toDate(),
        voteAverage = vote_average,
        popularity = popularity,
        backdropPath = backdrop_path ?: "",
    )
}

fun ApiMovieDetails.toMovieDetails(): MovieDetails {
    return MovieDetails(
        id = id,
        title = title,
        overview = overview,
        posterPath = poster_path,
        releaseDate = release_date.toDate(),
        voteAverage = vote_average,
        popularity = popularity,
        backdropPath = backdrop_path ?: "",
        genres = genres,
        tagline = tagline,
    )
}

fun MovieDetails.toMovie(): Movie {
    return Movie(
        id,
        title,
        overview,
        posterPath,
        releaseDate,
        voteAverage,
        popularity,
        backdropPath,
        isFavorite
    )
}

fun Movie.toEntity() = MovieEntity(
    id,
    title,
    overview,
    posterPath,
    releaseDate,
    voteAverage,
    popularity,
    backdropPath
)