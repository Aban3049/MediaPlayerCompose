package com.abanapps.videoplayer.data_layer.roomDatabase

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(version = 1, entities = [FavouriteSongs::class], exportSchema = false)
abstract class SongsDatabase : RoomDatabase() {
    abstract fun songsDao(): SongsDao
}

