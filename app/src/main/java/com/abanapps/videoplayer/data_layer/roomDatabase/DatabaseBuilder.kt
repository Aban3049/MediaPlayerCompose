package com.abanapps.videoplayer.data_layer.roomDatabase

import android.content.Context
import androidx.room.Room

fun databaseBuilder(context: Context): SongsDatabase {
    return Room.databaseBuilder(
        context,
        SongsDatabase::class.java,
        "songs.db"
    ).build()
}