package com.naol.moviemania.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.naol.moviemania.domain.model.Movie

@Entity(tableName = "movie")
data class MovieEntity(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String,
    val releaseDate: String,
    val voteAverage: Double,
    val popularity: Double,
    val backdropPath: String
)


fun MovieEntity.toDomain() = Movie(
    id,
    title,
    overview,
    posterPath,
    releaseDate,
    voteAverage,
    popularity,
    backdropPath
)
