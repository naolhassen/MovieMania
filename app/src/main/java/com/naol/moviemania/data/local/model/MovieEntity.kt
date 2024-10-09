package com.naol.moviemania.data.local.model

import androidx.room.Entity

@Entity(tableName = "movie")
data class MovieEntity(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String,
    val releaseDate: String,
    val voteAverage: Double,
    val popularity: Double,
    val backdropPath: String
)