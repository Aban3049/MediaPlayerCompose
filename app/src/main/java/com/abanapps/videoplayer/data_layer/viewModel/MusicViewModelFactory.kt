package com.abanapps.videoplayer.data_layer.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.abanapps.videoplayer.data_layer.Repo.RoomRepo

class MusicViewModelFactory(private val repository: RoomRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RoomViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RoomViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
