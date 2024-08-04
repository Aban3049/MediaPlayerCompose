package com.abanapps.videoplayer.data_layer.Repo

import android.app.Application
import android.content.ContentResolver
import android.content.Context
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import com.abanapps.videoplayer.data_layer.videofile.VideoFile
import com.abanapps.videoplayer.domain_layer.Repo.VideoAppRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class VideoAppRepoImpl:VideoAppRepo {

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
            MediaStore.Video.Media.TITLE
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
                    title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE))
                )
                allVideo.add(videoFile)
            }
        } ?: run {
            // Handle null cursor case
            Log.e("VideoAppRepoImpl", "Failed to query video files.")
        }

        emit(allVideo)
    }
}





//class VideoAppRepoImpl : VideoAppRepo {
//
//    override suspend fun getAllVideos(application: Application): Flow<ArrayList<VideoFile>> = flow {
//        val allVideo = ArrayList<VideoFile>()
//
//        val projection = arrayOf(
//            MediaStore.Video.Media.DISPLAY_NAME,
//            MediaStore.Video.Media.DATA,
//            MediaStore.Video.Media._ID,
//            MediaStore.Video.Media.DURATION,
//            MediaStore.Video.Media.ARTIST,
//            MediaStore.Video.Media.DATE_ADDED,
//            MediaStore.Video.Media.SIZE,
//            MediaStore.Video.Media.TITLE
//        )
//
//        val uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
//        val memoryCursor = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            application.contentResolver.query(uri, projection, null, null, null)
//        } else {
//            val projection2 = arrayOf(MediaStore.Files.FileColumns.DATA)
//            val selection = "${MediaStore.Files.FileColumns.MIME_TYPE} = ?"
//            val selectionArgs = arrayOf("video/*")
//            val uri2 = MediaStore.Files.getContentUri("external")
//            application.contentResolver.query(uri2, projection2, selection, selectionArgs, null)
//        }
//
//        memoryCursor?.use { cursor ->
//            while (cursor.moveToNext()) {
//                val videoFile = VideoFile(
//                    fileName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)),
//                    path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)),
//                    id = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)),
//                    duration = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)),
//                    artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.ARTIST)),
//                    dateAdded = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_ADDED)),
//                    size = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)),
//                    title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE))
//                )
//                allVideo.add(videoFile)
//            }
//        } ?: run {
//            // Handle null cursor case
//            // For example, log an error or return an empty list.
//            Log.e("VideoAppRepoImpl", "Failed to query video files.")
//        }
//
//        emit(allVideo)
//    }
//}


//class VideoAppRepoImpl : VideoAppRepo {
//
//    override suspend fun getAllVideos(application: Application): Flow<ArrayList<VideoFile>> {
//
//        val allVideo = ArrayList<VideoFile>()
//
//        val projection = arrayOf(
//            MediaStore.Video.Media.DISPLAY_NAME,
//            MediaStore.Video.Media.DATA,
//            MediaStore.Video.Media._ID,
//            MediaStore.Video.Media.DURATION,
//            MediaStore.Video.Media.ARTIST,
//            MediaStore.Video.Media.DATE_ADDED,
//            MediaStore.Video.Media.SIZE,
//            MediaStore.Video.Media.TITLE
//        )
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
//            val memoryCursor =
//                application.contentResolver.query(uri, projection, null, null)
//            if (memoryCursor != null) {
//                while (memoryCursor.moveToNext()) {
//                    val fileName = memoryCursor.getString(0)
//                    val path = memoryCursor.getString(1)
//                    val id = memoryCursor.getString(2)
//                    val duration = memoryCursor.getString(3)
//                    val artist = memoryCursor.getString(4)
//                    val dateAdded = memoryCursor.getString(5)
//                    val size = memoryCursor.getString(6)
//                    val title = memoryCursor.getString(7)
//
//                    val videoFile = VideoFile(
//                        fileName = fileName,
//                        path = path,
//                        id = id,
//                        duration = duration,
//                        artist = artist,
//                        dateAdded = dateAdded,
//                        size = size,
//                        title = title
//                    )
//                    allVideo.add(videoFile)
//                }
//                memoryCursor.close()
//            }
//
//        } else {
//            val projection2 = arrayOf(MediaStore.Files.FileColumns.DATA)
//            val selection = "${MediaStore.Files.FileColumns.MIME_TYPE} = ?"
//            val selectionArgs = arrayOf("video/*")
//            val uri2 = MediaStore.Files.getContentUri("external")
//            val memoryCursor2 =
//                application.contentResolver.query(uri2, projection2, selection, selectionArgs, null)
//
//            if (memoryCursor2 != null) {
//                while (memoryCursor2.moveToNext()) {
//                    val fileName = memoryCursor2.getString(0)
//                    val path = memoryCursor2.getString(1)
//                    val id = memoryCursor2.getString(2)
//                    val duration = memoryCursor2.getString(3)
//                    val artist = memoryCursor2.getString(4)
//                    val dateAdded = memoryCursor2.getString(5)
//                    val size = memoryCursor2.getString(6)
//                    val title = memoryCursor2.getString(7)
//
//                    val videoFile = VideoFile(
//                        fileName = fileName,
//                        path = path,
//                        id = id,
//                        duration = duration,
//                        artist = artist,
//                        dateAdded = dateAdded,
//                        size = size,
//                        title = title
//                    )
//
//                    allVideo.add(videoFile)
//
//                }
//                memoryCursor2.close()
//            }
//
//
//        }
//
//        return flow {
//            emit(allVideo)
//        }
//
//    }
//
//
//}


