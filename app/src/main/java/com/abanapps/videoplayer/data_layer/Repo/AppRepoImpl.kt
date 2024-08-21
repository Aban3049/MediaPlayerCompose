package com.abanapps.videoplayer.data_layer.Repo

import android.app.Application
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import com.abanapps.videoplayer.data_layer.audioFile.AudioFile
import com.abanapps.videoplayer.data_layer.mediaFile.MediaFiles
import com.abanapps.videoplayer.data_layer.mediaFile.MediaPath
import com.abanapps.videoplayer.data_layer.videofile.VideoFile
import com.abanapps.videoplayer.domain_layer.Repo.AppRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File


class AppRepoImpl : AppRepo {

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

        val sortOrder = "${MediaStore.Video.VideoColumns.DISPLAY_NAME} ASC"
        val memoryCursor = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
            application.contentResolver.query(uri, projection, null, null, sortOrder)
        } else {
            val selection = "${MediaStore.Files.FileColumns.MIME_TYPE} LIKE ?"
            val selectionArgs = arrayOf("video/%")
            val uri = MediaStore.Files.getContentUri("external")
            application.contentResolver.query(uri, projection, selection, selectionArgs, sortOrder)
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

        val sortOrder = "${MediaStore.Audio.AudioColumns.DISPLAY_NAME} ASC"
        val memoryCursor = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            application.contentResolver.query(uri, projection, null, null, sortOrder)
        } else {

            val selection =
                "${MediaStore.Files.FileColumns.MIME_TYPE} LIKE ? AND ${MediaStore.Audio.Media.DURATION} >= ?"
            val selectionArgs = arrayOf("audio/%", "15000")
            val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            application.contentResolver.query(uri, projection, selection, selectionArgs, sortOrder)
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

    data class MediaFiles(
        val uri: String,
        val path: String,
        val name: String,
    )

    data class MediaPath(
        val folderName: String,
        val fileCount: Int,
    )


    override suspend fun getAllMediaFiles(application: Application): Flow<List<com.abanapps.videoplayer.data_layer.mediaFile.MediaPath>> = flow {
        val allMediaFiles = mutableListOf<MediaFiles>()


        val projection = arrayOf(
            MediaStore.Files.FileColumns._ID,
            MediaStore.Files.FileColumns.DISPLAY_NAME,
            MediaStore.Files.FileColumns.DATA
        )


        val selection = "${MediaStore.Files.FileColumns.MIME_TYPE} LIKE ? OR ${MediaStore.Files.FileColumns.MIME_TYPE} LIKE ?"
        val selectionArgs = arrayOf(
            "audio/%",
            "video/%"
        )
        val queryUri = MediaStore.Files.getContentUri("external")

        // 쿼리 실행
        val cursor = application.contentResolver.query(
            queryUri,
            projection,
            selection,
            selectionArgs,
            null
        )

        cursor?.use { memoryCursor ->
            while (memoryCursor.moveToNext()) {
                val mediaFiles = MediaFiles(
                    uri = memoryCursor.getString(memoryCursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID)),
                    path = memoryCursor.getString(memoryCursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA)),
                    name = memoryCursor.getString(memoryCursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DISPLAY_NAME))
                )
                allMediaFiles.add(mediaFiles)
            }
        }

        val groupedByFolder = allMediaFiles.groupBy {
            File(it.path).parentFile?.name.orEmpty()
        }.map { (folderName, files) ->
            com.abanapps.videoplayer.data_layer.mediaFile.MediaPath(folderName = folderName, fileCount = files.size)
        }


        emit(groupedByFolder)
    }
}















