package com.naol.moviemania.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.naol.moviemania.data.local.model.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: MovieEntity)

    @Query("SELECT * FROM movie")
    suspend fun getAllMovies(): List<MovieEntity>

    @Delete
    suspend fun delete(movie: MovieEntity)

    @Query("DELETE FROM movie")
    suspend fun deleteAll()
}