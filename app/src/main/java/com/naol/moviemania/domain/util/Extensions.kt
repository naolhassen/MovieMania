package com.naol.moviemania.domain.util

import java.text.SimpleDateFormat
import java.util.Locale

fun String.toDate(): String {
    val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(this)
    val formatter = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    return formatter.format(date)
}