package com.naol.moviemania.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.naol.moviemania.data.local.dao.MovieDao
import com.naol.moviemania.data.local.model.MovieEntity


@Database(entities = [MovieEntity::class], version = 1)
abstract class MovieManiaDatabase : RoomDatabase() {
    companion object {
        const val DATABASE_NAME = "movie_database"
    }

    abstract fun movieDao(): MovieDao
}
