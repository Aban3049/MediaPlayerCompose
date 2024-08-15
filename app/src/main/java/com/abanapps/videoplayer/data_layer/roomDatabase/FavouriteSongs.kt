package com.abanapps.videoplayer.data_layer.roomDatabase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_songs")
data class FavouriteSongs(

    @PrimaryKey(autoGenerate = true,)
    val id: Int? = null,

    val path: String,
    val title: String,
    val artist: String,
)