package com.abanapps.videoplayer.ui_layer.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.abanapps.videoplayer.data_layer.viewModel.RoomViewModel
import com.abanapps.videoplayer.ui_layer.Screens.FavouriteSongsScreen
import com.abanapps.videoplayer.ui_layer.Screens.Folders
import com.abanapps.videoplayer.ui_layer.Screens.HomeScreen
import com.abanapps.videoplayer.ui_layer.Screens.MainHomeScreen
import com.abanapps.videoplayer.ui_layer.Screens.MusicPlayerScreen
import com.abanapps.videoplayer.ui_layer.Screens.MusicScreen
import com.abanapps.videoplayer.ui_layer.Screens.VideoView
import com.abanapps.videoplayer.ui_layer.Screens.Videos
import kotlinx.serialization.Serializable

@Composable
fun AppNavigation(roomViewModel: RoomViewModel) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.HomeScreen) {

        composable<Routes.HomeScreen> {
            HomeScreen(navController)
        }

        composable<Routes.VideosScreen> {
            Videos(navHostController = navController)
        }

        composable<Routes.FolderScreen> {
            Folders(navHostController = navController)
        }

        composable<Routes.PLayerScreen> {
            val videoUrl: Routes.PLayerScreen = it.toRoute<Routes.PLayerScreen>()
            VideoView(videoUri = videoUrl.videoUri)
        }

        composable<Routes.MusicPlayerScreen> {
            val musicUrl: Routes.MusicPlayerScreen = it.toRoute<Routes.MusicPlayerScreen>()
            val title: Routes.MusicPlayerScreen = it.toRoute<Routes.MusicPlayerScreen>()
            MusicPlayerScreen(uri = musicUrl.musicUri,title = title.title ?: "Unknown", roomViewModel = roomViewModel)
        }

        composable<Routes.MainHomeScreen> {
            MainHomeScreen(navController)
        }

        composable<Routes.MusicScreen> {
            MusicScreen(navHostController = navController)
        }

        composable<Routes.FavoriteSongsScreen> {
            FavouriteSongsScreen(navController,roomViewModel)
        }

    }

}


sealed class Routes() {

    @Serializable
    data object HomeScreen

    @Serializable
    data object FolderScreen

    @Serializable
    data object VideosScreen

    @Serializable
    data object MainHomeScreen

    @Serializable
    data class PLayerScreen(val videoUri: String)

    @Serializable
    data object MusicScreen

    @Serializable
    data object FavoriteSongsScreen

    @Serializable
    data class MusicPlayerScreen(val musicUri: String,val title:String)

}