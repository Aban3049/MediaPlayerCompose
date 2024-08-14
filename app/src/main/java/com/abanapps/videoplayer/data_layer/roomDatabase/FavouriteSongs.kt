package com.abanapps.videoplayer.data_layer.roomDatabase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_songs")
data class FavouriteSongs(

    @PrimaryKey(autoGenerate = true)
    val id: Int? = 0,

    val path: String,
    val title: String,
    val artist: String,
)