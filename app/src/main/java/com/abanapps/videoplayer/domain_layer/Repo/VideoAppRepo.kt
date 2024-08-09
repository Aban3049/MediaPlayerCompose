package com.abanapps.videoplayer.domain_layer.Repo

import android.app.Application
import android.content.Context
import com.abanapps.videoplayer.data_layer.audioFile.AudioFile
import com.abanapps.videoplayer.data_layer.videofile.VideoFile
import kotlinx.coroutines.flow.Flow


interface VideoAppRepo {

   suspend fun getAllVideos(application: Application): Flow<ArrayList<VideoFile>>

   suspend fun getAllAudios(application: Application): Flow<ArrayList<AudioFile>>

}