package com.abanapps.videoplayer.data_layer.mediaFile


data class MediaFiles(
    val uri:String,
    val path:String,
    val name:String,
    val lastModified:Long = 0,

)