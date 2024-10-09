package com.naol.moviemania.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.naol.moviemania.data.local.MovieManiaDatabase.Companion.DATABASE_NAME
import com.naol.moviemania.data.local.dao.MovieDao
import com.naol.moviemania.data.local.model.MovieEntity


@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
abstract class MovieManiaDatabase : RoomDatabase() {
    companion object {
        const val DATABASE_NAME = "movie_database"
    }

    abstract fun movieDao(): MovieDao
}

fun provideDatabase(context: Context): MovieManiaDatabase {
    return Room.databaseBuilder(
        context,
        MovieManiaDatabase::class.java,
        DATABASE_NAME
    )
        .fallbackToDestructiveMigration()
        .build()
}