package com.abanapps.videoplayer.data_layer.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.net.Uri
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.OptIn
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.NotificationCompat
import androidx.media.app.NotificationCompat.MediaStyle
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import com.abanapps.videoplayer.MainActivity
import com.abanapps.videoplayer.R
import com.abanapps.videoplayer.ui_layer.Screens.MusicServiceAction
import com.abanapps.videoplayer.ui_layer.Utils.Utils

class MusicService : Service() {

    private lateinit var exoPlayer: ExoPlayer
    private lateinit var mediaSession: MediaSession
    private val CHANNEL_ID = "music_channel"
    private var currentTitle: String = "Unknown"
    var isPlaying = mutableStateOf(false)

    private var isLoopEnabled = false
    private var isShuffleEnabled = false
    private val binder = MusicServiceBinder()

    inner class MusicServiceBinder : Binder() {
        fun getService(): MusicService = this@MusicService
    }

    override fun onCreate() {
        super.onCreate()

        exoPlayer = ExoPlayer.Builder(this).build()
        createNotificationChannel()

        val audioAttributes = androidx.media3.common.AudioAttributes.Builder()
            .setUsage(C.USAGE_MEDIA)
            .setContentType(C.AUDIO_CONTENT_TYPE_MUSIC)
            .build()
        exoPlayer.setAudioAttributes(audioAttributes, true)

        mediaSession = MediaSession.Builder(this, exoPlayer).build()

        exoPlayer.addListener(object : Player.Listener {
            override fun onIsPlayingChanged(isPlaying1: Boolean) {
                updateNotification()
                isPlaying.value = isPlaying1
            }
        })
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val uriString = intent?.getStringExtra("music_uri")
        currentTitle = intent?.getStringExtra("music_title") ?: "Unknown"

        if (uriString != null) {
            try {

                val uri = Uri.parse(uriString)
                val mediaItem = MediaItem.fromUri(uri)
                exoPlayer.setMediaItem(mediaItem)
                exoPlayer.prepare()
                exoPlayer.play()
            } catch (e: Exception) {
                Log.e("MusicService", "Error playing media: ${e.message}")
                stopSelf()
            }
        }

        when (intent?.action) {

            MusicServiceAction.PLAY.action -> playOrPause()

            MusicServiceAction.PAUSE.action -> exoPlayer.pause()

            MusicServiceAction.SEEK.action -> {
                val position = intent.getLongExtra("seek_position", 0L)
                seekTo(position)
            }

            MusicServiceAction.LOOP.action -> {
                exoPlayer.repeatMode = if (exoPlayer.repeatMode == Player.REPEAT_MODE_ONE) {
                    Player.REPEAT_MODE_OFF
                } else {
                    Player.REPEAT_MODE_ONE
                }
            }

            MusicServiceAction.SHUFFLE.action -> {
                exoPlayer.shuffleModeEnabled = !exoPlayer.shuffleModeEnabled
            }

            MusicServiceAction.FORWARD.action -> skipToNext()
            MusicServiceAction.BACKWARD.action -> skipToPrevious()
        }
        return START_NOT_STICKY
    }


    private fun playOrPause() {
        if (exoPlayer.isPlaying) {
            Utils.isPlaying.value = true
            exoPlayer.pause()
        } else {
            exoPlayer.play()
            Utils.isPlaying.value = false
        }
    }


    private fun seekTo(position: Long) {
        exoPlayer.seekTo(position)
    }

    private fun skipToNext() {
        exoPlayer.seekForward()
    }

    private fun skipToPrevious() {
        exoPlayer.seekBack()
    }


    private fun toggleShuffle() {
        isShuffleEnabled = !isShuffleEnabled
        exoPlayer.shuffleModeEnabled = isShuffleEnabled
    }

    private fun toggleLoop() {
        isLoopEnabled = !isLoopEnabled
        exoPlayer.repeatMode =
            if (isLoopEnabled) Player.REPEAT_MODE_ONE else Player.REPEAT_MODE_OFF
    }

    @OptIn(UnstableApi::class)
    private fun createNotification(): Notification {
        val playPauseIcon = if (exoPlayer.isPlaying) R.drawable.pause else R.drawable.playm
        val playPauseAction = if (exoPlayer.isPlaying) "PAUSE" else "PLAY"


        val openAppIntent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val openAppPendingIntent = PendingIntent.getActivity(
            this,
            0,
            openAppIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Now Playing")
            .setContentText(currentTitle)
            .setSmallIcon(R.drawable.musicicon)
            .setContentIntent(openAppPendingIntent) // Set the content intent
            .addAction(R.drawable.previous, "Previous", getPendingIntent("BACKWARD"))
            .addAction(playPauseIcon, "Play/Pause", getPendingIntent(playPauseAction))
            .addAction(R.drawable.forward, "Next", getPendingIntent("FORWARD"))
            .setStyle(
                MediaStyle()
                    .setMediaSession(mediaSession.sessionCompatToken)
                    .setShowActionsInCompactView(0, 1, 2)
            )
            .setOnlyAlertOnce(true)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setOngoing(exoPlayer.isPlaying)
            .build()
    }

    private fun updateNotification() {
        val notification = createNotification()
        startForeground(1, notification)
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

    private fun getPendingIntent(action: String): PendingIntent {
        val intent = Intent(this, MusicService::class.java).apply {
            this.action = action
        }
        return PendingIntent.getService(
            this,
            action.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        exoPlayer.release()
        mediaSession.release()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }
}

