package com.abanapps.videoplayer.ui_layer.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.abanapps.videoplayer.R
import com.abanapps.videoplayer.ui_layer.viewModel.PlayerViewModel
import kotlinx.coroutines.delay

@Composable
fun MusicPlayerScreen(uri: String, title: String?, viewModel: PlayerViewModel = hiltViewModel()) {

    val mediaUri = remember { mutableStateOf(uri) }
    val mediaTitle = remember { mutableStateOf(title) }
    val isLoopEnabled = remember { mutableStateOf(false) }
    val isShuffleEnabled = remember { mutableStateOf(false) }
    val playing = remember { mutableStateOf(false) }

    val allMusic = viewModel.musicList.collectAsState()
    val context = LocalContext.current

    val totalDuration = remember { mutableLongStateOf(0L) }
    val progress = remember { mutableFloatStateOf(0f) }
    val currentPosition = remember { mutableLongStateOf(0L) }

    fun calculateProgressValue(position: Long) {
        if (totalDuration.value > 0) {
            progress.value = (position.toFloat() / totalDuration.value) * 100f
        }
    }

    val shuffledList = remember(allMusic.value, isShuffleEnabled.value) {
        if (isShuffleEnabled.value) allMusic.value.shuffled() else allMusic.value
    }

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            val mediaItem = MediaItem.fromUri(mediaUri.value)
            setMediaItem(mediaItem)
            prepare()
            playWhenReady = true
        }
    }

    LaunchedEffect(mediaUri.value) {
        exoPlayer.setMediaItem(MediaItem.fromUri(mediaUri.value))
        exoPlayer.prepare()
        exoPlayer.playWhenReady = true
    }

    DisposableEffect(true) {
        onDispose {
            exoPlayer.release()
        }
    }

    exoPlayer.addListener(object : Player.Listener {
        override fun onPlaybackStateChanged(state: Int) {
            if (state == Player.STATE_READY) {
                totalDuration.value = exoPlayer.duration
            }
            if (state == Player.STATE_ENDED) {
                // Move to the next song
                val currentIndex = allMusic.value.indexOfFirst { it.path == mediaUri.value }
                val nextIndex = (currentIndex + 1) % allMusic.value.size
                mediaUri.value = allMusic.value[nextIndex].path
                mediaTitle.value = allMusic.value[nextIndex].title
            }
        }

        override fun onIsPlayingChanged(isPlaying: Boolean) {
            super.onIsPlayingChanged(isPlaying)
            playing.value = isPlaying
        }
    })

    LaunchedEffect(Unit) {
        while (true) {
            currentPosition.value = exoPlayer.currentPosition
            calculateProgressValue(currentPosition.value)
            delay(500L)
        }
    }

    exoPlayer.repeatMode =
        if (isLoopEnabled.value) Player.REPEAT_MODE_ONE else Player.REPEAT_MODE_OFF

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

                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(2.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.StarOutline,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.padding()
                        )
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
                }) {
                    Icon(
                        imageVector = Icons.Default.Shuffle,
                        contentDescription = null,
                        tint = Color(0xFFcac9ce)
                    )
                }

                IconButton(onClick = {
                    isLoopEnabled.value = !isLoopEnabled.value // Toggle loop mode
                    exoPlayer.repeatMode = if (isLoopEnabled.value) {
                        Player.REPEAT_MODE_ONE
                    } else {
                        Player.REPEAT_MODE_OFF
                    }
                }) {
                    Icon(
                        imageVector = if (isLoopEnabled.value) Icons.Default.RepeatOne else Icons.Default.Repeat,
                        contentDescription = null,
                        tint = Color(0xFFcac9ce)
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
                            exoPlayer.seekTo(newPosition.toLong())
                            calculateProgressValue(newPosition.toLong())
                        },
                        onValueChangeFinished = {
                            val newPosition = (progress.value / 100f) * totalDuration.value
                            exoPlayer.seekTo(newPosition.toLong())
                            calculateProgressValue(newPosition.toLong())
                        },
                        valueRange = 0f..100f,
                        colors = SliderDefaults.colors(
                            inactiveTrackColor = Color(0xFF7b7a7f),
                            activeTrackColor = Color(0xFFb0afb4),
                            thumbColor = Color.Transparent
                        ),
                        modifier = Modifier
                            .weight(1f)
                    )

                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Absolute.SpaceBetween
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
                    exoPlayer.seekToPrevious()
                }) {
                    Icon(
                        imageVector = Icons.Default.FastRewind,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(120.dp)
                    )
                }

                IconButton(onClick = {
                    if (exoPlayer.isPlaying) {
                        exoPlayer.pause()
                    } else {
                        exoPlayer.play()
                    }
                }) {
                    Icon(
                        if (playing.value) Icons.Default.Pause else Icons.Default.PlayArrow,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(120.dp)
                    )
                }

                IconButton(onClick = {
                    exoPlayer.seekForward()
                }) {
                    Icon(
                        imageVector = Icons.Default.FastForward,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(120.dp)
                    )
                }

            }


        }


    }
}

//fun MusicPlayerScreen(uri: String, title: String?, viewModel: PlayerViewModel = hiltViewModel()) {
//
//
//    val mediaUri = remember {
//        mutableStateOf(uri)
//    }
//
//    val mediaTitle = remember {
//        mutableStateOf(title)
//    }
//
//    val isLoopEnabled = remember { mutableStateOf(false) }
//    val isShuffleEnabled = remember { mutableStateOf(false) }
//    val playing = remember {
//        mutableStateOf(false)
//    }
//
//    val allMusic = viewModel.musicList.collectAsState()
//    val isLoading = viewModel.showUi.collectAsState()
//    val context = LocalContext.current
//
//    val totalDuration = remember { mutableLongStateOf(0L) }
//    val progress = remember { mutableFloatStateOf(0f) }
//    val currentPosition = remember { mutableLongStateOf(0L) }
//
//    fun calculateProgressValue(position: Long) {
//        if (totalDuration.value > 0) {
//            progress.value = (position.toFloat() / totalDuration.value) * 100f
//        }
//    }
//
//    val shuffledList = remember(allMusic.value, isShuffleEnabled.value) {
//        if (isShuffleEnabled.value) allMusic.value.shuffled() else allMusic.value
//    }
//
//
//    val exoPlayer = remember {
//        ExoPlayer.Builder(context).build().apply {
//            val mediaItem = MediaItem.fromUri(mediaUri.value)
//            setMediaItem(mediaItem)
//            prepare()
//            playWhenReady = true
//        }
//    }
//
//    // Update ExoPlayer's media item whenever the mediaUri changes
//    LaunchedEffect(mediaUri.value) {
//        exoPlayer.setMediaItem(MediaItem.fromUri(mediaUri.value))
//        exoPlayer.prepare()
//        exoPlayer.playWhenReady = true
//    }
//
//
//    DisposableEffect(true) {
//        onDispose {
//            exoPlayer.release()
//        }
//    }
//
//    exoPlayer.addListener(object : Player.Listener {
//        override fun onPlaybackStateChanged(state: Int) {
//            if (state == Player.STATE_ENDED) {
//                // Move to the next song
//                val currentIndex = allMusic.value.indexOfFirst { it.path == mediaUri.value }
//                val nextIndex =
//                    (currentIndex + 1) % allMusic.value.size // Loop back to the first song
//                mediaUri.value = allMusic.value[nextIndex].path
//                mediaTitle.value = allMusic.value[nextIndex].title
//            }
//        }
//
//        override fun onIsPlayingChanged(isPlaying: Boolean) {
//            super.onIsPlayingChanged(isPlaying)
//            playing.value = isPlaying
//        }
//
//    })
//
//    exoPlayer.repeatMode =
//        if (isLoopEnabled.value) Player.REPEAT_MODE_ONE else Player.REPEAT_MODE_OFF
//
//
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(
//                brush = Brush.horizontalGradient(
//                    colors = listOf(
//                        Color(0xFF59585e),
//                        Color(0xFF56555a),
//                        Color(0xFF3b3b3d)
//                    )
//                )
//            )
//            .padding(18.dp)
//    ) {
//
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(bottom = 5.dp)
//        ) {
//
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Card(
//                    modifier = Modifier
//                        .width(60.dp)
//                        .height(65.dp),
//                    shape = RoundedCornerShape(8.dp),
//                    colors = CardDefaults.cardColors(Color(0xFF504f54))
//                ) {
//                    Row(
//                        Modifier.fillMaxSize(),
//                        verticalAlignment = Alignment.CenterVertically,
//                        horizontalArrangement = Arrangement.Center
//                    ) {
//                        Icon(
//                            painter = painterResource(id = R.drawable.musicicon),
//                            contentDescription = null,
//                            tint = Color(0xFF656469),
//                            modifier = Modifier
//                                .size(42.dp)
//                                .padding(4.dp)
//
//                        )
//                    }
//
//                }
//
//                Text(
//                    text = mediaTitle.value ?: "Unknown",
//                    color = Color.White,
//                    style = MaterialTheme.typography.bodyLarge,
//                    fontSize = 16.sp,
//                    maxLines = 1,
//                    overflow = TextOverflow.Ellipsis,
//                    modifier = Modifier
//                        .padding(start = 12.dp, end = 12.dp)
//                        .weight(1f)
//                )
//
//                Card(
//                    colors = CardDefaults.cardColors(Color(0xFF6e6d72)), modifier = Modifier
//                        .clip(CircleShape)
//
//                ) {
//                    Row(
//                        verticalAlignment = Alignment.CenterVertically,
//                        horizontalArrangement = Arrangement.Center,
//                        modifier = Modifier.padding(2.dp)
//                    ) {
//                        Icon(
//                            imageVector = Icons.Outlined.StarOutline,
//                            contentDescription = null,
//                            tint = Color.White,
//                            modifier = Modifier.padding()
//                        )
//                    }
//
//                }
//
//                Spacer(modifier = Modifier.width(8.dp))
//
//                Card(
//                    colors = CardDefaults.cardColors(Color(0xFF6e6d72)), modifier = Modifier
//                        .clip(
//                            CircleShape
//                        )
//
//                ) {
//                    Row(
//                        verticalAlignment = Alignment.CenterVertically,
//                        horizontalArrangement = Arrangement.Center,
//                        modifier = Modifier.padding(2.dp)
//                    ) {
//                        Icon(
//                            imageVector = Icons.Default.MoreHoriz,
//                            contentDescription = null,
//                            tint = Color.White,
//                            modifier = Modifier.padding()
//                        )
//                    }
//
//                }
//
//
//            }
//
//            Row(
//                Modifier
//                    .fillMaxWidth()
//                    .padding(top = 6.dp)
//            ) {
//
//            }
//
//
//        }
//
//        Spacer(modifier = Modifier.height(6.dp))
//
//        Row(modifier = Modifier.fillMaxWidth()) {
//            Text(
//                text = "Playing Next",
//                color = Color.White,
//                style = MaterialTheme.typography.bodyLarge,
//                fontWeight = FontWeight.SemiBold,
//                fontSize = 18.sp
//            )
//
//            Spacer(modifier = Modifier.weight(1f))
//
//            IconButton(onClick = {
//                isShuffleEnabled.value = !isShuffleEnabled.value
//            }) {
//                Icon(
//                    imageVector = Icons.Default.Shuffle,
//                    contentDescription = null,
//                    tint = Color(0xFFcac9ce)
//                )
//            }
//
//            IconButton(onClick = {
//                isLoopEnabled.value = !isLoopEnabled.value // Toggle loop mode
//                exoPlayer.repeatMode = if (isLoopEnabled.value) {
//                    Player.REPEAT_MODE_ONE
//                } else {
//                    Player.REPEAT_MODE_OFF
//                }
//            }) {
//                Icon(
//                    imageVector = if (isLoopEnabled.value) Icons.Default.RepeatOne else Icons.Default.Repeat,
//                    contentDescription = null,
//                    tint = Color(0xFFcac9ce)
//                )
//            }
//
//
//        }
//
//        LazyColumn(modifier = Modifier.weight(1f)) {
//
//            itemsIndexed(shuffledList) { _, music ->
//                Column(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(bottom = 5.dp)
//                        .clickable {
//                            mediaUri.value = music.path
//                            mediaTitle.value = music.title
//                        }
//                ) {
//
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        Card(
//                            modifier = Modifier
//                                .width(50.dp)
//                                .height(55.dp),
//                            shape = RoundedCornerShape(8.dp),
//                            colors = CardDefaults.cardColors(Color(0xFF262628))
//                        ) {
//                            Row(
//                                Modifier.fillMaxSize(),
//                                verticalAlignment = Alignment.CenterVertically,
//                                horizontalArrangement = Arrangement.Center
//                            ) {
//                                Icon(
//                                    painter = painterResource(id = R.drawable.musicicon),
//                                    contentDescription = null,
//                                    tint = Color(0xFF606062),
//                                    modifier = Modifier
//                                        .size(30.dp)
//                                        .padding(4.dp)
//
//                                )
//                            }
//
//                        }
//
//                        Text(
//                            text = music.title ?: "Unknown Title",
//                            color = Color.White,
//                            style = MaterialTheme.typography.bodyLarge,
//                            fontSize = 16.sp,
//                            maxLines = 1,
//                            overflow = TextOverflow.Ellipsis,
//                            modifier = Modifier
//                                .padding(start = 12.dp, end = 12.dp)
//                                .weight(1f)
//                        )
//
//                        Icon(
//                            imageVector = Icons.Default.MoreHoriz,
//                            contentDescription = null,
//                            tint = Color.White,
//                            modifier = Modifier.padding(top = 3.dp, start = 2.dp)
//                        )
//
//                    }
//
//                }
//
//
//            }
//
//        }
//
//        Row(modifier = Modifier.fillMaxWidth()) {
//
//            Text(
//                text = formatDuration(currentPosition.longValue),
//                color = Color.White,
//                fontSize = 16.sp,
//                modifier = Modifier.weight(1f)
//            )
//
//            Slider(
//                value = progress.floatValue,
//                onValueChange = { value ->
//                    val newPosition = (value / 100f) * totalDuration.longValue
//                    exoPlayer.seekTo(newPosition.toLong())
//                    calculateProgressValue(newPosition.toLong())
//                },
//                onValueChangeFinished = {
//                    // 여기서 슬라이더의 값을 확정짓고, 사용자 클릭 위치에서 재생하도록 하는 코드입니다.
//                    val newPosition = (progress.floatValue / 100f) * totalDuration.longValue
//                    exoPlayer.seekTo(newPosition.toLong())
//                    calculateProgressValue(newPosition.toLong())
//                },
//                valueRange = 0f..100f,
//                colors = SliderDefaults.colors(
//                    inactiveTrackColor = Color(0xFF7b7a7f),
//                    activeTrackColor = Color(0xFFb0afb4)
//                ),
//                modifier = Modifier.weight(1f)
//            )
//
//            Text(
//                text = formatDuration(totalDuration.longValue),
//                color = Color.White,
//                modifier = Modifier.weight(1f)
//            )
//        }
//
//
//
//
//        Spacer(modifier = Modifier.height(15.dp))
//
//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.Center
//        ) {
//
//            IconButton(onClick = {
//                exoPlayer.seekToPrevious()
//            }) {
//                Icon(
//                    imageVector = Icons.Default.FastForward,
//                    contentDescription = null,
//                    tint = Color.White,
//                    modifier = Modifier.size(70.dp)
//                )
//            }
//
//            IconButton(onClick = {
//                if (exoPlayer.isPlaying) {
//                    exoPlayer.pause()
//                } else {
//                    exoPlayer.play()
//                }
//            }) {
//                Icon(
//                    if (playing.value) Icons.Default.Pause else Icons.Default.PlayArrow,
//                    contentDescription = null,
//                    tint = Color.White,
//                    modifier = Modifier.size(70.dp)
//                )
//            }
//
//            IconButton(onClick = {
//                exoPlayer.seekForward()
//            }) {
//                Icon(
//                    imageVector = Icons.Default.FastForward,
//                    contentDescription = null,
//                    tint = Color.White,
//                    modifier = Modifier.size(70.dp)
//                )
//            }
//
//        }
//
//
//    }
//
//
//}

fun formatDuration(duration: Long): String {
    val minutes = (duration / 1000) / 60
    val seconds = (duration / 1000) % 60
    return String.format("%02d:%02d", minutes, seconds)
}

