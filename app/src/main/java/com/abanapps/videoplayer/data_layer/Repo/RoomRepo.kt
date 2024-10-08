package com.abanapps.videoplayer.data_layer.Repo


import com.abanapps.videoplayer.data_layer.roomDatabase.FavouriteSongs
import com.abanapps.videoplayer.data_layer.roomDatabase.SongsDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RoomRepo (private val dao: SongsDao) {

    fun getAllSongs(): Flow<List<FavouriteSongs>> = dao.getAllSongs()

    suspend fun upsertSong(song: FavouriteSongs) {
        dao.upsertSong(song)
    }

    suspend fun deleteSong(song: FavouriteSongs) {
        dao.deleteSong(song)
    }

    suspend fun getSong(title:String){
        dao.getSongByTitle(title)
    }



}
