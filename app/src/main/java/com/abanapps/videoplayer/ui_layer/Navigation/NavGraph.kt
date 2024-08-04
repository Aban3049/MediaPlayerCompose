package com.abanapps.videoplayer.ui_layer.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.internal.rememberComposableLambdaN
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.abanapps.videoplayer.ui_layer.Screens.Folders
import com.abanapps.videoplayer.ui_layer.Screens.HomeScreen
import com.abanapps.videoplayer.ui_layer.Screens.Videos
import kotlinx.serialization.Serializable

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.HomeScreen) {

        composable<Routes.HomeScreen> {
           HomeScreen(navController)
        }
        
        composable<Routes.FolderScreen>{
            Videos(navHostController = navController)
        }
        
        composable<Routes.FolderScreen> { 
            Folders(navHostController = navController)
        }

        composable<Routes.PLayerScreen> {

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
    data class PLayerScreen(val videoUri: String)

}