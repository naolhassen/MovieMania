package com.naol.moviemania.domain.model

import com.naol.moviemania.data.remote.model.Genre
import com.naol.moviemania.data.remote.model.ProductionCompany
import com.naol.moviemania.data.remote.model.ProductionCountry
import com.naol.moviemania.data.remote.model.SpokenLanguage


data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String,
    val releaseDate: String,
    val voteAverage: Double,
    val popularity: Double,
    val backdropPath: String,
    val isFavorite: Boolean = false
)

