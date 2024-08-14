package com.abanapps.videoplayer.ui_layer.Screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.abanapps.videoplayer.data_layer.viewModel.RoomViewModel

@Composable
fun FavouriteSongsScreen(roomViewModel: RoomViewModel) {

    val allFavouriteSongs by roomViewModel.allFavouriteSongs.collectAsState(initial = emptyList())

    Column(Modifier.fillMaxSize()) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()

        ) {

            items(allFavouriteSongs) { songs ->

                Row {
                    Text(text = songs.title, color = Color.White, fontSize = 22.sp)

                }

            }

        }

    }


}