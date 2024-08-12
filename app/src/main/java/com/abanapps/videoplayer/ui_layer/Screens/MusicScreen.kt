package com.abanapps.videoplayer.ui_layer.Screens

import android.content.Intent
import android.net.Uri
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
import androidx.compose.material.icons.filled.KeyboardVoice
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.abanapps.videoplayer.R
import com.abanapps.videoplayer.data_layer.service.MusicService
import com.abanapps.videoplayer.ui_layer.Navigation.Routes
import com.abanapps.videoplayer.ui_layer.viewModel.PlayerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MusicScreen(
    navHostController: NavHostController, viewModel: PlayerViewModel = hiltViewModel()
) {


    LaunchedEffect(true) {
        viewModel.loadAllMusic()
    }

    val allMusic = viewModel.musicList.collectAsState()
    val isLoading = viewModel.showUi.collectAsState()

    val context = LocalContext.current

    Scaffold(

        topBar = {
            TopAppBar(title = {
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
        } else if (allMusic.value.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it), contentAlignment = Alignment.Center
            ) {
                Text(text = "No Music Found")
            }
        } else {
            Column(
                Modifier
                    .padding(it)
                    .fillMaxSize()
                    .background(Color(0xFF000000))
            ) {

                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp)
                ) {

                    Text(
                        text = "Songs",
                        modifier = Modifier.padding(top = 3.dp, start = 3.dp),
                        color = Color.White,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 34.sp
                    )

                    Card(
                        Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(Color(0xFF1c1c1e))
                    ) {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = null,
                                tint = Color(0xFF99989d)
                            )

                            Text(
                                text = "Find in Songs",
                                color = Color(0xFFFF99989d),
                                modifier = Modifier.padding(start = 7.dp)
                            )

                            Spacer(modifier = Modifier.weight(1f))

                            Icon(
                                imageVector = Icons.Default.KeyboardVoice,
                                contentDescription = null,
                                tint = Color(0xFF99989d),
                                modifier = Modifier
                            )


                        }

                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(7.dp)
                    ) {

                        Button(
                            onClick = { },
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 7.dp),
                            colors = ButtonDefaults.buttonColors(Color(0xFF1c1c1e)),
                            shape = RoundedCornerShape(8.dp)
                        ) {

                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = null,
                                tint = Color(0xFFf92d48)
                            )

                            Text(text = "Play", color = Color(0xFFda3859))

                        }

                        Button(
                            onClick = { },
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 7.dp),
                            colors = ButtonDefaults.buttonColors(Color(0xFF1c1c1e)),
                            shape = RoundedCornerShape(8.dp)
                        ) {

                            Icon(
                                imageVector = Icons.Default.Shuffle,
                                contentDescription = null,
                                tint = Color(0xFFf92d48)
                            )

                            Text(text = "Shuffle", color = Color(0xFFda3859))

                        }

                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    LazyColumn {

                        items(allMusic.value) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 5.dp)
                                    .clickable {
                                        val intent =
                                            Intent(context, MusicService::class.java).apply {
                                                action = "PLAY"
                                                putExtra(
                                                    "music_uri",
                                                    Uri.parse(it.path)
                                                )
                                            }
                                        context.startService(intent)

                                        navHostController.navigate(
                                            Routes.MusicPlayerScreen(
                                                musicUri = it.path,
                                                title = it.title!!
                                            )
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
                                        colors = CardDefaults.cardColors(Color(0xFF262628))
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
                                        text = it.title ?: "Unknown Title",
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

                                Row(
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(top = 6.dp)
                                ) {
                                    Spacer(
                                        modifier = Modifier
                                            .height(1.dp)
                                            .weight(0.2f)
                                            .fillMaxWidth()
                                            .background(Color.Transparent)
                                    )

                                    Spacer(
                                        modifier = Modifier
                                            .height(1.dp)
                                            .weight(1f)
                                            .fillMaxWidth()
                                            .padding(start = 1.dp)
                                            .background(Color(0xFF313131))
                                    )
                                }


                            }


                        }

                    }


                }


            }
        }


    }

}

