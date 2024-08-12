package com.abanapps.videoplayer.data_layer.service

import android.app.Application
import android.app.Notification
import android.app.Service
import android.content.Intent
import android.net.Uri
import android.os.IBinder
import androidx.compose.runtime.Composable
import androidx.core.app.NotificationCompat
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.abanapps.videoplayer.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.os.Build
import androidx.annotation.OptIn
import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FastRewind
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaSession

class MusicService : Service() {

    private lateinit var exoPlayer: ExoPlayer
    private lateinit var mediaSession: MediaSession
    private val CHANNEL_ID = "music_channel"


    override fun onCreate() {
        super.onCreate()

        // Create ExoPlayer instance
        exoPlayer = ExoPlayer.Builder(this).build()
        createNotificationChannel()

        // Set audio attributes
        val audioAttributes = androidx.media3.common.AudioAttributes.Builder()
            .setUsage(C.USAGE_MEDIA)
            .setContentType(C.AUDIO_CONTENT_TYPE_MOVIE)
            .build()
        exoPlayer.setAudioAttributes(audioAttributes, true)

        // Create MediaSession
        mediaSession = MediaSession.Builder(this, exoPlayer)
            .setSessionActivity(getPendingIntent("PLAY"))
            .build()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            "PLAY" -> {
                val uri = intent.getParcelableExtra<Uri>("music_uri")
                if (uri != null) {
                    playMusic(uri)
                }
            }
            "BACKWARD" -> skipBackward()
            "FORWARD" -> skipForward()
        }

        val notification = createNotification()
        startForeground(1, notification)
        return START_STICKY
    }

    private fun playMusic(uri: Uri) {
        val mediaItem = MediaItem.fromUri(uri)
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
        exoPlayer.play()
    }

    private fun createNotification(): Notification {
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Playing Music")
            .setContentText("Music is playing")
            .setSmallIcon(R.drawable.musicicon)
            .addAction(R.drawable.ic_launcher_foreground, "Backward", getPendingIntent("BACKWARD"))
            .addAction(R.drawable.ic_launcher_foreground, "Forward", getPendingIntent("FORWARD"))
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()
    }

    private fun getPendingIntent(action: String): PendingIntent {
        val intent = Intent(this, MusicService::class.java).apply {
            this.action = action
        }
        return PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
    }

    private fun skipForward() {
        exoPlayer.seekTo(exoPlayer.currentPosition + 10000) // Skip forward 10 seconds
    }

    private fun skipBackward() {
        exoPlayer.seekTo(exoPlayer.currentPosition - 10000) // Skip backward 10 seconds
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "Music Playback",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        exoPlayer.release()
        mediaSession.release()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}





