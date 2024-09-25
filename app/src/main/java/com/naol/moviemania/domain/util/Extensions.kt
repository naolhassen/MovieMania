package com.naol.moviemania.domain.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
fun String.toDate(): String {
    val date = LocalDate.parse(this, DateTimeFormatter.ISO_DATE)
    return date.format(DateTimeFormatter.ofPattern("MMM dd, yyyy"))
}