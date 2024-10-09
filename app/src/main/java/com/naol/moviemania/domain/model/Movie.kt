package com.naol.moviemania.domain.model

import com.naol.moviemania.data.local.model.MovieEntity

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String,
    val releaseDate: String,
    val voteAverage: Double,
    val popularity: Double,
    val backdropPath: String
)


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