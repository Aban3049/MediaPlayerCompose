package com.abanapps.videoplayer.ui_layer.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abanapps.videoplayer.data_layer.Repo.RoomRepo
import com.abanapps.videoplayer.data_layer.audioFile.AudioFile
import com.abanapps.videoplayer.data_layer.roomDatabase.FavouriteSongs
import com.abanapps.videoplayer.data_layer.videofile.VideoFile
import com.abanapps.videoplayer.domain_layer.Repo.VideoAppRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val repo: VideoAppRepo,
    val application: Application
) : ViewModel()
{
    val showUi = MutableStateFlow(false)
    val videoList = MutableStateFlow(emptyList<VideoFile>())
    val musicList = MutableStateFlow(emptyList<AudioFile>())
    private val isLoading = MutableStateFlow(false)


    fun loadAllVideos() {
        isLoading.value = true
        viewModelScope.launch {
            repo.getAllVideos(application).collectLatest {
                videoList.value = it
                Log.d("ALLMEDIA", "loadAllVideos: ${videoList.value}")
            }
            isLoading.value = false
        }
    }

    fun loadAllMusic() {
        isLoading.value = true
        viewModelScope.launch {
            repo.getAllAudios(application).collectLatest {
                musicList.value = it
                Log.d("ALLMEDIA", "loadAllMusic: ${musicList.value} ")
            }
            isLoading.value = false
        }
    }



}
