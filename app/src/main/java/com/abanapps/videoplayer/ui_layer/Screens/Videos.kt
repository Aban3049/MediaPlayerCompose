package com.abanapps.videoplayer.ui_layer.Screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.abanapps.videoplayer.ui_layer.viewModel.PlayerViewModel

@Composable
fun Videos(navHostController: NavHostController, viewModel: PlayerViewModel = hiltViewModel()) {

    LaunchedEffect(true) {
        viewModel.loadAllVideos()
    }

    val allVideos = viewModel.videoList.collectAsState()
    val isLoading = viewModel.showUi.collectAsState()

    if (isLoading.value) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else if (allVideos.value.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "No Videos Found")
        }
    } else {
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)) {
            items(allVideos.value) {

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp),
                    shape = RoundedCornerShape(10.dp)
                ) {



                }
            }

        }
    }


}