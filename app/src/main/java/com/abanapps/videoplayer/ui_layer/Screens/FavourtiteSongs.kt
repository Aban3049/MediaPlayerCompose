package com.abanapps.videoplayer.ui_layer.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.abanapps.videoplayer.R
import com.abanapps.videoplayer.data_layer.viewModel.RoomViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouriteSongsScreen(navHostController: NavHostController, roomViewModel: RoomViewModel) {

    val allFavouriteSongs by roomViewModel.allFavouriteSongs.collectAsState(initial = emptyList())

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
                    text = "Favourite Songs",
                    modifier = Modifier.padding(top = 3.dp, start = 3.dp),
                    color = Color.White,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 34.sp
                )

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()

                ) {

                    items(allFavouriteSongs) { songs ->

                        Row(
                            modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
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
                                text = songs.title ?: "Unknown Title",
                                color = Color.White,
                                style = MaterialTheme.typography.bodyLarge,
                                fontSize = 16.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Clip,
                                modifier = Modifier
                                    .padding(start = 12.dp, end = 12.dp)
                                    .weight(1f)
                            )



                        }
                    }

                }


            }

        }


    }
}