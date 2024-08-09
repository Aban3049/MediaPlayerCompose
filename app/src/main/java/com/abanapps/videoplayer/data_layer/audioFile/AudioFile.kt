package com.abanapps.videoplayer.data_layer.audioFile

data class AudioFile(
    val fileName: String?,
    val path: String,
    val id: String?,
    val duration: Long?,
    val artist: String?,
    val dateAdded: String?,
    val size: Long?,
    val title: String?,
)