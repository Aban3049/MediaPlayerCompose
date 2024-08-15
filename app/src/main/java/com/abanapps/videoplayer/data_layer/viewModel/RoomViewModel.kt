package com.abanapps.videoplayer.data_layer.viewModel

import androidx.annotation.WorkerThread
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abanapps.videoplayer.data_layer.Repo.RoomRepo
import com.abanapps.videoplayer.data_layer.roomDatabase.FavouriteSongs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RoomViewModel(private val songRoomRepo: RoomRepo) : ViewModel() {

    val allFavouriteSongs = songRoomRepo.getAllSongs()

    fun upsertSong(songs: FavouriteSongs) =
        viewModelScope.launch {
            songRoomRepo.upsertSong(songs)
        }


    fun deleteSong(songs: FavouriteSongs) =
        viewModelScope.launch {
            songRoomRepo.deleteSong(songs)
        }

    fun getSongByTitle(title: String) = viewModelScope.launch(Dispatchers.IO) {
        songRoomRepo.getSong(title)
    }

}