package com.abanapps.videoplayer.ui_layer.Screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun HomeScreen(navHostController: NavHostController) {
    Pager(navHostController)
}