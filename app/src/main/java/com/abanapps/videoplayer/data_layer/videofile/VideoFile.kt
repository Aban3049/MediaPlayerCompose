package com.abanapps.videoplayer.data_layer.videofile

data class VideoFile(
    val fileName: String?,
    val path: String,
    val id: String?,
    val duration: Long?,
    val artist: String?,
    val dateAdded: String?,
    val size: Long?,
    val title: String?,
    val mimeType: String?
)
