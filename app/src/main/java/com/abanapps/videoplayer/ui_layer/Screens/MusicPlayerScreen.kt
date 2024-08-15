package com.abanapps.videoplayer.ui_layer.Screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FastForward
import androidx.compose.material.icons.filled.FastRewind
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.RepeatOne
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import com.abanapps.videoplayer.R
import com.abanapps.videoplayer.data_layer.roomDatabase.FavouriteSongs
import com.abanapps.videoplayer.data_layer.service.MusicService
import com.abanapps.videoplayer.data_layer.viewModel.RoomViewModel
import com.abanapps.videoplayer.ui_layer.viewModel.PlayerViewModel
import kotlinx.coroutines.launch

fun formatDuration(duration: Long): String {
    val minutes = (duration / 1000) / 60
    val seconds = (duration / 1000) % 60
    return String.format("%02d:%02d", minutes, seconds)
}

@Composable
fun MusicPlayerScreen(
    uri: String,
    title: String?,
    roomViewModel: RoomViewModel,
    viewModel: PlayerViewModel = hiltViewModel(),
    context: Context = LocalContext.current
) {

    val isSongInFavourite = remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()


    LaunchedEffect(true) {
        viewModel.loadAllMusic()
    }

    val mediaUri = remember { mutableStateOf(uri) }
    val mediaTitle = remember { mutableStateOf(title) }
    val isLoopEnabled = remember { mutableStateOf(false) }
    val isShuffleEnabled = remember { mutableStateOf(false) }
    val playing = remember { mutableStateOf(false) }
    val progress = remember { mutableFloatStateOf(0f) }
    val currentPosition = remember { mutableLongStateOf(0L) }
    val totalDuration = remember { mutableLongStateOf(0L) }

    val allMusic = viewModel.musicList.collectAsState()

    val shuffledList = remember(allMusic.value, isShuffleEnabled.value) {
        if (isShuffleEnabled.value) allMusic.value.shuffled() else allMusic.value
    }

    LaunchedEffect(mediaTitle.value) {
        scope.launch {
            val song = roomViewModel.getSongByTitle(mediaTitle.value ?: "")
            isSongInFavourite.value = song != null
        }
    }



    fun sendCommandToService(
        action: String,
        uri: Uri? = null,
        seekPosition: Long? = null
    ) {
        val intent = Intent(context, MusicService::class.java).apply {
            this.action = action
            uri?.let { putExtra("music_uri", it.toString()) }
            seekPosition?.let { putExtra("seek_position", it) }
            putExtra("music_title", mediaTitle.value)
        }
        ContextCompat.startForegroundService(context, intent)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFF59585e),
                        Color(0xFF56555a),
                        Color(0xFF3b3b3d)
                    )
                )
            )
            .padding(18.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 5.dp)
        ) {


            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Card(
                    modifier = Modifier
                        .width(60.dp)
                        .height(65.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(Color(0xFF504f54))
                ) {
                    Row(
                        Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.musicicon),
                            contentDescription = null,
                            tint = Color(0xFF656469),
                            modifier = Modifier
                                .size(42.dp)
                                .padding(4.dp)

                        )
                    }

                }

                Text(
                    text = mediaTitle.value ?: "Unknown",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(start = 12.dp, end = 12.dp)
                        .weight(1f)
                )

                Card(
                    colors = CardDefaults.cardColors(Color(0xFF6e6d72)), modifier = Modifier
                        .clip(CircleShape)
                        .clickable {
                            roomViewModel.upsertSong(
                                songs = FavouriteSongs(
                                    path = mediaUri.value,
                                    title = mediaTitle.value ?: "Unknown",
                                    artist = "Unknown"
                                )
                            )
                            isSongInFavourite.value = true
                        }

                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(2.dp)
                    ) {
                        if (isSongInFavourite.value) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.padding()
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Outlined.StarOutline,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.padding()
                            )
                        }

                    }

                }

                Spacer(modifier = Modifier.width(8.dp))

                Card(
                    colors = CardDefaults.cardColors(Color(0xFF6e6d72)), modifier = Modifier
                        .clip(
                            CircleShape
                        )

                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(2.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreHoriz,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.padding()
                        )
                    }

                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Playing Next",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp
                )

                Spacer(modifier = Modifier.weight(1f))

                IconButton(onClick = {
                    isShuffleEnabled.value = !isShuffleEnabled.value
                    sendCommandToService(MusicServiceAction.SHUFFLE.action)
                }) {
                    Icon(
                        imageVector = Icons.Default.Shuffle,
                        contentDescription = null,
                        tint = if (isShuffleEnabled.value) Color(0xFF00FF00) else Color(0xFFcac9ce)
                    )
                }

                IconButton(onClick = {
                    isLoopEnabled.value = !isLoopEnabled.value
                    sendCommandToService(MusicServiceAction.LOOP.action)
                }) {
                    Icon(
                        imageVector = if (isLoopEnabled.value) Icons.Default.RepeatOne else Icons.Default.Repeat,
                        contentDescription = null,
                        tint = if (isLoopEnabled.value) Color(0xFF00FF00) else Color(0xFFcac9ce)
                    )
                }
            }

            LazyColumn(modifier = Modifier.weight(1f)) {
                itemsIndexed(shuffledList) { _, music ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 5.dp)
                            .clickable {
                                mediaUri.value = music.path
                                mediaTitle.value = music.title
                                sendCommandToService(
                                    MusicServiceAction.PLAY.action,
                                    uri = music.path.toUri(),
                                )
                            }
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Card(
                                modifier = Modifier
                                    .width(50.dp)
                                    .height(55.dp),
                                shape = RoundedCornerShape(8.dp),
                                colors = CardDefaults.cardColors(Color(0xFF504f54))
                            ) {
                                Row(
                                    Modifier.fillMaxSize(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.musicicon),
                                        contentDescription = null,
                                        tint = Color(0xFF606062),
                                        modifier = Modifier
                                            .size(30.dp)
                                            .padding(4.dp)
                                    )
                                }
                            }

                            Text(
                                text = music.title ?: "Unknown Title",
                                color = Color.White,
                                style = MaterialTheme.typography.bodyLarge,
                                fontSize = 16.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier
                                    .padding(start = 12.dp, end = 12.dp)
                                    .weight(1f)
                            )

                            Icon(
                                imageVector = Icons.Default.MoreHoriz,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.padding(top = 3.dp, start = 2.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(7.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Slider(
                        value = progress.value,
                        onValueChange = { value ->
                            val newPosition = (value / 100f) * totalDuration.value
                            sendCommandToService(
                                MusicServiceAction.SEEK.action,
                                seekPosition = newPosition.toLong()
                            )
                            progress.value = value
                        },
                        valueRange = 0f..100f,
                        colors = SliderDefaults.colors(
                            inactiveTrackColor = Color(0xFF7b7a7f),
                            activeTrackColor = Color(0xFFb0afb4),
                            thumbColor = Color.Transparent
                        ),
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = formatDuration(currentPosition.value),
                        color = Color(0xFF8e8d92),
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 14.sp,
                    )
                    Text(
                        text = formatDuration(totalDuration.value),
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 14.sp,
                        color = Color(0xFF8e8d92),
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                IconButton(onClick = {
                    sendCommandToService(MusicServiceAction.BACKWARD.action)
                }) {
                    Icon(
                        imageVector = Icons.Default.FastRewind,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(40.dp)
                    )
                }

                IconButton(onClick = {
                    if (playing.value) {
                        sendCommandToService(MusicServiceAction.PAUSE.action)
                    } else {
                        sendCommandToService(
                            MusicServiceAction.PLAY.action,
                            Uri.parse(mediaUri.value)
                        )
                    }
                    playing.value = !playing.value
                }) {
                    Icon(
                        if (playing.value) Icons.Default.Pause else Icons.Default.PlayArrow,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(40.dp)
                    )
                }

                IconButton(onClick = {
                    sendCommandToService(MusicServiceAction.FORWARD.action)
                }) {
                    Icon(
                        imageVector = Icons.Default.FastForward,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(40.dp)
                    )
                }
            }
        }


    }
}


enum class MusicServiceAction(val action: String) {
    PLAY("PLAY"),
    PAUSE("PAUSE"),
    FORWARD("FORWARD"),
    BACKWARD("BACKWARD"),
    SEEK("SEEK"),
    SHUFFLE("SHUFFLE"),
    LOOP("LOOP"),
    STOP("STOP")
}




