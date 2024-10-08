package com.abanapps.videoplayer.ui_layer.Screens

import android.content.Context
import android.media.AudioManager
import android.provider.Settings
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.abanapps.videoplayer.ui_layer.Utils.exoPlayerSaver

@Composable
fun VideoView(videoUri: String) {
    val lifeCycle by remember { mutableStateOf(Lifecycle.Event.ON_CREATE) }
    val context = LocalContext.current

    val mediaItem2 = MediaItem.fromUri(videoUri)

    val exoPlayer = rememberSaveable(context, videoUri, saver = exoPlayerSaver(context, mediaItem2)) {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(mediaItem2)
            playWhenReady = true
            prepare()
        }
    }


    val lifeCycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current
    DisposableEffect(lifeCycleOwner, exoPlayer) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_PAUSE -> exoPlayer.pause()
                Lifecycle.Event.ON_RESUME -> exoPlayer.play()
                Lifecycle.Event.ON_DESTROY -> exoPlayer.release()
                else -> {}
            }
        }
        lifeCycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifeCycleOwner.lifecycle.removeObserver(observer)
        }
    }


    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = {
            CustomPlayerView(context, exoPlayer)
        },
        update = {
            when (lifeCycle) {
                Lifecycle.Event.ON_PAUSE -> {
                    it.onPause()
                    it.player?.pause()
                }

                Lifecycle.Event.ON_RESUME -> {
                    it.onResume()
                }

                else -> Unit
            }
        }
    )
}

class CustomPlayerView(context: Context, exoPlayer: ExoPlayer) : PlayerView(context) {
    private val gestureDetector = GestureDetector(context, GestureListener(context, exoPlayer))

    init {
        player = exoPlayer
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        gestureDetector.onTouchEvent(event!!)
        return super.onTouchEvent(event)
    }
}

class GestureListener(private val context: Context, private val exoPlayer: ExoPlayer) :
    GestureDetector.SimpleOnGestureListener() {
    private val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    private var initialVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
    private var initialBrightness =
        Settings.System.getInt(context.contentResolver, Settings.System.SCREEN_BRIGHTNESS, 0)

    override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        e1 ?: return false
        e2 ?: return false

        val deltaY = e1.y - e2.y
        val deltaX = e1.x - e2.x

        if (Math.abs(deltaX) > Math.abs(deltaY)) {
            // Horizontal swipe - Seek
            exoPlayer.seekTo(exoPlayer.currentPosition + (deltaX * 1000).toLong())
        } else {
            // Vertical swipe - Adjust volume or brightness
            if (e1.x < context.resources.displayMetrics.widthPixels / 2) {
                // Left side - Adjust brightness
                val newBrightness = (initialBrightness + deltaY).toInt().coerceIn(0, 255)
                Settings.System.putInt(
                    context.contentResolver,
                    Settings.System.SCREEN_BRIGHTNESS,
                    newBrightness
                )
            } else {
                // Right side - Adjust volume
                val newVolume = (initialVolume + deltaY / 10).toInt()
                    .coerceIn(0, audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC))
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, newVolume, 0)
            }
        }
        return true
    }
}



