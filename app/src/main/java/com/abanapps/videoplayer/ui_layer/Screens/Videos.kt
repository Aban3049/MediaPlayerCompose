package com.abanapps.videoplayer.ui_layer.Screens


import android.webkit.MimeTypeMap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.VideoFrameDecoder
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.abanapps.videoplayer.R
import com.abanapps.videoplayer.ui_layer.Navigation.Routes
import com.abanapps.videoplayer.ui_layer.viewModel.PlayerViewModel


//0xFF191722 background color
//Card(
//modifier = Modifier
//.fillMaxSize()
//.padding(12.dp),
//elevation = CardDefaults.cardElevation(14.dp),
//shape = RoundedCornerShape(13.dp),
//colors = CardDefaults.cardColors(Color(0xFF1f2130))
//) { card code
//0xFF393b4a path text color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Videos(navHostController: NavHostController, viewModel: PlayerViewModel = hiltViewModel()) {

    LaunchedEffect(true) {
        viewModel.loadAllVideos()
    }

    val context = LocalContext.current

    val imageLoader = ImageLoader.Builder(context)
        .components {
            add(VideoFrameDecoder.Factory())
        }
        .memoryCachePolicy(CachePolicy.ENABLED)
        .diskCachePolicy(CachePolicy.ENABLED)
        .build()


    val allVideos = viewModel.videoList.collectAsState()
    val isLoading = viewModel.showUi.collectAsState()

    Scaffold(
        topBar = {
            androidx.compose.material3.TopAppBar(title = {
                Text(
                    text = "Library", color = Color(0xFFfc2b49),
                    style = MaterialTheme.typography.titleLarge,
                )
            },
                navigationIcon = {
                    IconButton(onClick = {
                        navHostController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = null,
                            tint = Color(0xFFfc293d)
                        )
                    }
                },
                modifier = Modifier,
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF000000)),
                actions = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Default.FilterList,
                            contentDescription = null,
                            tint = Color(0xFFfd294d)
                        )
                    }
                })
        }
    ) {
        if (isLoading.value) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it), contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (allVideos.value.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it), contentAlignment = Alignment.Center
            ) {
                Text(text = "No Videos Found")
            }
        } else {
            Column(
                Modifier
                    .fillMaxSize()
                    .background(Color(0xFF000000))
                    .padding(it)
            ) {

                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp)
                ) {

                    Text(
                        text = "Videos",
                        modifier = Modifier.padding(top = 3.dp, start = 3.dp),
                        color = Color.White,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 34.sp
                    )
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(15.dp)
                ) {
                    items(allVideos.value) { video ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                                .clickable {
                                    navHostController.navigate(Routes.PLayerScreen(videoUri = video.path))
                                }
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Card(
                                    modifier = Modifier
                                        .height(85.dp)
                                        .width(120.dp),
                                    colors = CardDefaults.cardColors(Color.Transparent),
                                    shape = RoundedCornerShape(6.dp)
                                ) {
                                    AsyncImage(
                                        model = ImageRequest.Builder(context = context)
                                            .data(video.path.toUri())
                                            .build(),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .height(85.dp)
                                            .width(120.dp),
                                        contentScale = ContentScale.Crop,
                                        imageLoader = imageLoader
                                    )
                                }

                                Column(
                                    modifier = Modifier
                                        .padding(start = 12.dp)
                                ) {
                                    Text(
                                        text = video.title ?: "Unknown Title",
                                        style = MaterialTheme.typography.titleMedium,
                                        maxLines = 2,
                                        overflow = TextOverflow.Ellipsis,
                                        color = Color.White,
                                        modifier = Modifier.fillMaxWidth()
                                    )

                                    Text(
                                        text = getFolderName(video.path) ?: "Unknown Folder",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color(0xFFFF99989d),
                                        modifier = Modifier,
                                        fontSize = 14.sp
                                    )

                                    Row {
                                        Card(
                                            colors = CardDefaults.cardColors(Color(0xFF3e4050)),
                                            modifier = Modifier.padding(end = 5.dp),
                                            shape = RoundedCornerShape(6.dp)
                                        ) {
                                            Row(
                                                modifier = Modifier
                                                    .background(Color(0xFF212236))
                                                    .padding(2.dp),
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Center
                                            ) {
                                                Image(
                                                    painter = painterResource(id = R.drawable.playblack),
                                                    contentDescription = null,
                                                    modifier = Modifier.size(18.dp),
                                                    colorFilter = ColorFilter.tint(Color.White)
                                                )
                                                Text(
                                                    text = video.duration!!.toDurationString()
                                                        ?: "Unknown Duration",
                                                    color = Color.White,
                                                    fontSize = 11.sp
                                                )
                                            }
                                        }

                                        Card(
                                            colors = CardDefaults.cardColors(Color(0xFF3e4050)),
                                            modifier = Modifier.padding(start = 5.dp),
                                            shape = RoundedCornerShape(6.dp)
                                        ) {
                                            Row(
                                                modifier = Modifier
                                                    .background(Color(0xFF212236))
                                                    .padding(2.dp),
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Center
                                            ) {
                                                Image(
                                                    painter = painterResource(id = R.drawable.playm),
                                                    contentDescription = null,
                                                    modifier = Modifier.size(18.dp),
                                                )
                                                Text(text = " ")
                                                Text(
                                                    text = getFileExtensionFromMimeType(video.mimeType!!)
                                                        ?: "Unknown Format",
                                                    color = Color.White,
                                                    fontSize = 11.sp
                                                )
                                            }
                                        }
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Spacer(
                                modifier = Modifier
                                    .height(2.dp)
                                    .fillMaxWidth()
                                    .background(
                                        Color(0xFF1b1d2c)
                                    )
                            )


                        }
                    }
                }


            }
        }
    }
}

fun getFolderName(path: String): String? {
    val parts = path.split("/")
    return if (parts.size > 1) parts[parts.size - 2] else null
}

fun getFileExtensionFromMimeType(mimeType: String): String? {
    return MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType)
}

fun Long.toMegabytes(): Double = this / (1024.0 * 1024.0)
fun Long.toKilobytes(): Double = this / 1024.0
fun Long.toDurationString(): String {
    val totalSeconds = this / 1000
    val hours = totalSeconds / 3600
    val minutes = (totalSeconds % 3600) / 60
    val seconds = totalSeconds % 60
    return String.format("%02d:%02d:%02d", hours, minutes, seconds)
}
