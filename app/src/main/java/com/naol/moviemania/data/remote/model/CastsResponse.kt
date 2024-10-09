package com.naol.moviemania.data.remote.model

data class CastsResponse(
    val cast: List<Cast>,
    val crew: List<Crew>,
    val id: Int
)