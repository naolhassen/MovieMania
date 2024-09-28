package com.naol.moviemania.domain.util

import androidx.navigation.NavHostController
import java.text.SimpleDateFormat
import java.util.Locale

fun String.toDate(): String {
    val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(this)
    val formatter = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    return formatter.format(date)
}


fun NavHostController.canNavigateBack(): Boolean {
    return previousBackStackEntry?.destination != null
}