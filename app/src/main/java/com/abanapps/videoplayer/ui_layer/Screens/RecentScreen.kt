package com.abanapps.videoplayer.ui_layer.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.FilterList
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.abanapps.videoplayer.ui_layer.Utils.FileItem
import com.abanapps.videoplayer.ui_layer.viewModel.PlayerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecentScreen(navController: NavHostController, viewModel: PlayerViewModel = hiltViewModel()) {

    val mediaFiles = viewModel.mediaFileList.collectAsState(initial = emptyList())

    val isLoading = viewModel.isLoading.collectAsState()

    LaunchedEffect(true) {
        viewModel.loadMediaFiles()
    }


    Scaffold(
        topBar = {
            androidx.compose.material3.TopAppBar(title = {
                Text(
                    text = "Recent", color = Color(0xFFfc2b49),
                    style = MaterialTheme.typography.titleLarge,
                )
            },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
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
    ) { innerPadding ->

        Column(
            Modifier
                .fillMaxSize()
                .background(Color(0xFF000000))
                .padding(innerPadding)
        ) {

            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp,)
            ) {

                Text(
                    text = "Recent",
                    modifier = Modifier.padding(top = 3.dp, start = 3.dp, bottom = 10.dp),
                    color = Color.White,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 34.sp
                )

                Column(
                    Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF1c1c1e))
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
                                FileItem(it.folderName, it.fileCount.toString())
                            }

                        }

                    }

                }

            }
        }
    }
}