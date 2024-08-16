package com.abanapps.videoplayer.ui_layer.Screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.abanapps.videoplayer.data_layer.viewModel.RoomViewModel

@Composable
fun HomeScreen(navHostController: NavHostController,roomViewModel: RoomViewModel) {
    MainHomeScreen(navController = navHostController,roomViewModel)
}