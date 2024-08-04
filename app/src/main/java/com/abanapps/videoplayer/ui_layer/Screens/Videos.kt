package com.abanapps.videoplayer.ui_layer.Screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.abanapps.videoplayer.ui_layer.Navigation.Routes
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            items(allVideos.value) {

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                        .clickable{
                            navHostController.navigate(Routes.PLayerScreen(videoUri = it.path))
                        },
                    shape = RoundedCornerShape(10.dp)
                ) {

                    ConstraintLayout(modifier = Modifier.padding(8.dp)) {

                        val (title, duration, size) = createRefs()

                        Text(
                            text = it.title ?: "Unknown Title",
                            style = MaterialTheme.typography.titleMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier
                                .fillMaxWidth()
                                .constrainAs(title) {
                                    top.linkTo(parent.top)
                                    start.linkTo(parent.start)
                                })

                        Text(
                            text = it.duration!!.toDurationString(),
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.constrainAs(duration) {
                                top.linkTo(title.bottom)
                                start.linkTo(parent.start)
                            }
                        )

                        Text(
                            text = it.size!!.toMegabytes().toString(),
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.constrainAs(size) {
                                start.linkTo(duration.end, margin = 8.dp)
                                top.linkTo(title.bottom)
                            })

                    }

                }
            }

        }
    }


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
