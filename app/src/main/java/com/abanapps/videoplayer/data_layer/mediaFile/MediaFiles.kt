package com.abanapps.videoplayer.data_layer.mediaFile


data class MediaFiles(
    val uri: String,
    val path: String,
    val name: String,
)

data class MediaPath(
    val folderName: String,
    val fileCount: Int,
)
