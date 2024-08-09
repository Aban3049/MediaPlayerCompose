package com.abanapps.videoplayer.ui_layer.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.abanapps.videoplayer.ui_layer.Utils.TopAppBar
import com.abanapps.videoplayer.ui_layer.viewModel.PlayerViewModel

@Composable
fun MusicScreen(
    navHostController: NavHostController,
    viewModel: PlayerViewModel = hiltViewModel()
) {

    LaunchedEffect(true) {
        viewModel.loadAllMusic()
    }

    val allMusic = viewModel.musicList.collectAsState()
    val isLoading = viewModel.showUi.collectAsState()


    Scaffold(
        topBar = {
            TopAppBar(title = "Music", navHostController = navHostController)
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
                modifier = Modifier
                    .background(Color(0xFF191722))
                    .fillMaxSize()
                    .padding(it)
                    .padding(start = 10.dp, end = 10.dp)
            ) {

                Text(
                    text = "All Music Files:",
                    color = Color.White,
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = 19.sp,
                    modifier = Modifier.padding(start = 8.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                LazyColumn {

                    items(allMusic.value) {

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp)
                        ) {

                            Text(
                                text = it.title ?: "Unknown Title",
                                color = Color.White,
                                style = MaterialTheme.typography.bodyMedium,
                                fontSize = 17.sp,
                                maxLines = 1
                            )
                            Text(
                                text = it.artist ?: "Unknown Artist",
                                color = Color.White,
                                style = MaterialTheme.typography.bodyMedium,
                                fontSize = 14.sp
                            )

                            Text(
                                text = it.duration!!.toDurationString() ?: "Unknown Duration",
                                color = Color.White,
                                style = MaterialTheme.typography.bodyMedium,
                                fontSize = 14.sp
                            )
                            
                            Spacer(modifier = Modifier.height(8.dp))

                            Spacer(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(2.dp)
                                    .background(Color.White)
                                    .padding(start = 10.dp, end = 10.dp)
                            )

                        }




                    }

                }
            }


        }


    }

}