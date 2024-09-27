package com.naol.moviemania.domain.model

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
