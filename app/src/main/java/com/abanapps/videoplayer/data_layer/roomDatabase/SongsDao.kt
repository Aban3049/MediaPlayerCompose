package com.abanapps.videoplayer.data_layer.roomDatabase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface SongsDao {

    @Query("SELECT * FROM favourite_songs")
    fun getAllSongs(): Flow<List<FavouriteSongs>>

    @Delete
    suspend fun deleteSong(song: FavouriteSongs)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertSong(song: FavouriteSongs)

    @Query("SELECT * FROM favourite_songs WHERE title = :title")
    fun getSongByTitle(title: String): FavouriteSongs?

}
