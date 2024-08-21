package com.abanapps.videoplayer.domain_layer.Repo

import android.app.Application
import com.abanapps.videoplayer.data_layer.audioFile.AudioFile
import com.abanapps.videoplayer.data_layer.mediaFile.MediaFiles
import com.abanapps.videoplayer.data_layer.mediaFile.MediaPath
import com.abanapps.videoplayer.data_layer.videofile.VideoFile
import kotlinx.coroutines.flow.Flow


interface AppRepo {

   suspend fun getAllVideos(application: Application): Flow<ArrayList<VideoFile>>

   suspend fun getAllAudios(application: Application): Flow<ArrayList<AudioFile>>

   suspend fun getAllMediaFiles(application: Application): Flow<List<com.abanapps.videoplayer.data_layer.mediaFile.MediaPath>>
}