package com.abanapps.videoplayer.data_layer.Repo

import android.app.Application
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import com.abanapps.videoplayer.data_layer.audioFile.AudioFile
import com.abanapps.videoplayer.data_layer.videofile.VideoFile
import com.abanapps.videoplayer.domain_layer.Repo.VideoAppRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class VideoAppRepoImpl : VideoAppRepo {

    override suspend fun getAllVideos(application: Application): Flow<ArrayList<VideoFile>> = flow {
        val allVideo = ArrayList<VideoFile>()

        val projection = arrayOf(
            MediaStore.Video.Media.DISPLAY_NAME,
            MediaStore.Video.Media.DATA,
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.DURATION,
            MediaStore.Video.Media.ARTIST,
            MediaStore.Video.Media.DATE_ADDED,
            MediaStore.Video.Media.SIZE,
            MediaStore.Video.Media.TITLE,
            MediaStore.Video.Media.MIME_TYPE
        )

        val memoryCursor = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
            application.contentResolver.query(uri, projection, null, null, null)
        } else {
            val selection = "${MediaStore.Files.FileColumns.MIME_TYPE} LIKE ?"
            val selectionArgs = arrayOf("video/%")
            val uri = MediaStore.Files.getContentUri("external")
            application.contentResolver.query(uri, projection, selection, selectionArgs, null)
        }

        memoryCursor?.use { cursor ->
            while (cursor.moveToNext()) {
                val videoFile = VideoFile(
                    fileName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)),
                    path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)),
                    id = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)),
                    duration = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)),
                    artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.ARTIST)),
                    dateAdded = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_ADDED)),
                    size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)),
                    title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE)),
                    mimeType = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.MIME_TYPE))
                )
                allVideo.add(videoFile)
            }
        } ?: run {
            // Handle null cursor case
            Log.e("VideoAppRepoImpl", "Failed to query video files.")
        }

        emit(allVideo)
    }

    override suspend fun getAllAudios(application: Application): Flow<ArrayList<AudioFile>> = flow {

        val allMusicFiles = ArrayList<AudioFile>()

        val projection = arrayOf(
            MediaStore.Video.Media.DISPLAY_NAME,
            MediaStore.Video.Media.DATA,
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.DURATION,
            MediaStore.Video.Media.ARTIST,
            MediaStore.Video.Media.DATE_ADDED,
            MediaStore.Video.Media.SIZE,
            MediaStore.Video.Media.TITLE,
        )

        val memoryCursor = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
            application.contentResolver.query(uri, projection, null, null, null)
        } else {
            val selection = "${MediaStore.Files.FileColumns.MIME_TYPE} LIKE ?"
            val selectionArgs = arrayOf("audio/%")
            val uri = MediaStore.Files.getContentUri("external")
            application.contentResolver.query(uri, projection, selection, selectionArgs, null)
        }
        memoryCursor?.use { cursor ->
            while (cursor.moveToNext()) {
                val audioFile = AudioFile(
                    fileName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)),
                    path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)),
                    id = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)),
                    duration = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)),
                    artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.ARTIST)),
                    dateAdded = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_ADDED)),
                    size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)),
                    title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE)),
                )
                allMusicFiles.add(audioFile)
            }
        } ?: run {
            // Handle null cursor case
            Log.e("VideoAppRepoImpl", "Failed to query video files.")
        }
        emit(allMusicFiles)
    }

}








