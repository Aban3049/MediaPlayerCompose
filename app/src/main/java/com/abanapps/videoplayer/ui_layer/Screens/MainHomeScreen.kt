package com.abanapps.videoplayer.ui_layer.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Widgets
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.abanapps.videoplayer.R
import com.abanapps.videoplayer.data_layer.viewModel.RoomViewModel
import com.abanapps.videoplayer.ui_layer.Navigation.Routes
import com.abanapps.videoplayer.ui_layer.Utils.FileItem
import com.abanapps.videoplayer.ui_layer.viewModel.PlayerViewModel
import kotlin.random.Random

//0xFF191722 body color
//0xFF1f2130 card color
//0xFF393b4a text color
//(0xFF5c5d6f tint more icon color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainHomeScreen(
    navController: NavHostController,
    roomViewModel: RoomViewModel,
    viewModel: PlayerViewModel = hiltViewModel()
) {

    val favouriteSongsSize = roomViewModel.allFavouriteSongs.collectAsState(initial = emptyList())

    val audioListSize = viewModel.musicList.collectAsState(initial = emptyList())

    val videoList = viewModel.videoList.collectAsState(initial = emptyList())

    val mediaFiles = viewModel.mediaFileList.collectAsState(initial = emptyList())

    val isLoading = viewModel.isLoading.collectAsState()

    LaunchedEffect(true) {
        viewModel.loadAllMusic()
        viewModel.loadAllVideos()
        viewModel.loadMediaFiles()
    }

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    text = "Video Player",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 5.dp)
                )
            },
                navigationIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.playm),
                        contentDescription = null,
                        modifier = Modifier.size(34.dp)
                    )
                },
                modifier = Modifier.padding(start = 12.dp, end = 12.dp),
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
                actions = {
                    Image(
                        imageVector = Icons.Default.Widgets,
                        contentDescription = null,
                        modifier = Modifier.size(34.dp),
                        colorFilter = ColorFilter.tint(Color.White)
                    )
                })
        }

    )
    { innerPadding ->
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF000000))
                .padding(innerPadding)
        ) {

            val (topItem) = createRefs()

            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, end = 12.dp)
                .constrainAs(topItem) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }) {

                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp)
                ) {

                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 5.dp),
                        colors = CardDefaults.cardColors(Color(0xFF1c1c1e)),
                        elevation = CardDefaults.cardElevation(14.dp),
                        shape = RoundedCornerShape(13.dp)
                    ) {

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp)
                        ) {

                            Image(
                                painter = painterResource(id = R.drawable.time),
                                contentDescription = null,
                                modifier = Modifier.size(34.dp)
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "Recent",
                                color = Color.White,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.ExtraBold
                            )

                            Spacer(modifier = Modifier.height(5.dp))

                            Text(
                                text = "${mediaFiles.value.size} Files",
                                color = Color(0xFFFF99989d),
                                style = MaterialTheme.typography.bodySmall,
                                fontSize = 14.sp
                            )
                        }

                    }

                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 5.dp)
                            .clickable {
                                navController.navigate(Routes.FavoriteSongsScreen)
                            },
                        colors = CardDefaults.cardColors(Color(0xFF1c1c1e)),
                        elevation = CardDefaults.cardElevation(14.dp),
                        shape = RoundedCornerShape(13.dp)
                    ) {

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp)
                        ) {

                            Image(
                                painter = painterResource(id = R.drawable.favorite),
                                contentDescription = null,
                                Modifier.size(34.dp)
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "Favourite",
                                color = Color.White,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.ExtraBold
                            )

                            Spacer(modifier = Modifier.height(5.dp))


                            Text(
                                text = "${favouriteSongsSize.value.size} Files",
                                color = Color(0xFFFF99989d),
                                style = MaterialTheme.typography.bodySmall,
                                fontSize = 14.sp
                            )
                        }


                    }

                }

                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp, bottom = 12.dp)
                ) {

                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 5.dp)
                            .clickable {
                                navController.navigate(Routes.MusicScreen)
                            },
                        colors = CardDefaults.cardColors(Color(0xFF1c1c1e)),
                        elevation = CardDefaults.cardElevation(14.dp),
                        shape = RoundedCornerShape(13.dp)
                    ) {

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp)
                        ) {

                            Image(
                                painter = painterResource(id = R.drawable.music),
                                contentDescription = null,
                                modifier = Modifier.size(34.dp)
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "Audio",
                                color = Color.White,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.ExtraBold
                            )

                            Spacer(modifier = Modifier.height(5.dp))

                            Text(
                                text = "${audioListSize.value.size} Files",
                                color = Color(0xFFFF99989d),
                                style = MaterialTheme.typography.bodySmall,
                                fontSize = 14.sp
                            )
                        }

                    }

                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 5.dp)
                            .clickable {
                                navController.navigate(Routes.VideosScreen)
                            },
                        colors = CardDefaults.cardColors(Color(0xFF1c1c1e)),
                        elevation = CardDefaults.cardElevation(14.dp),
                        shape = RoundedCornerShape(13.dp)
                    ) {

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp)
                        ) {

                            Image(
                                painter = painterResource(id = R.drawable.video),
                                contentDescription = null,
                                Modifier.size(34.dp)
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "Videos",
                                color = Color.White,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.ExtraBold
                            )

                            Spacer(modifier = Modifier.height(5.dp))


                            Text(
                                text = "${videoList.value.size} Files",
                                color = Color(0xFFFF99989d),
                                style = MaterialTheme.typography.bodySmall,
                                fontSize = 14.sp
                            )
                        }


                    }

                }

                Text(
                    text = "Recently Used",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 10.dp)
                )

                Column(
                    Modifier
                        .fillMaxWidth()
                ) {

                    Card(
                        Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(Color(0xFF1c1c1e)),
                        elevation = CardDefaults.cardElevation(14.dp),
                        shape = RoundedCornerShape(13.dp)
                    ) {

                        if (isLoading.value) {
                            FileItem("Downloads", "20 Files")
                            FileItem("Camera", "70 Files")
                            FileItem("SnapChat", "30 Files")
                            FileItem("Instagram", "0 Files")
                            FileItem("DCIM", "70 Files")
                            FileItem("Video", "0 Files")

                        } else {
                            LazyColumn {

                                items(mediaFiles.value) {
                                    FileItem(it.folderName,it.fileCount.toString())
                                }

                            }

                        }


                    }


                }


            }

        }
    }


}


fun randomColor(): Color {
    val random = Random.nextInt(Colors.entries.size)
    return Colors.entries[random].color
}

enum class Colors(val color: Color) {
    COLOR1(Color(0xFFf8774f)),
    COLOR2(Color(0xFFfcd247)),
    COLOR3(Color(0xFF458df7)),
    COLOR4(Color(0xFF41e259)),
    COLOR5(Color(0xFF7f838f))
}