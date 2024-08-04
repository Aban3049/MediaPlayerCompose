package com.abanapps.videoplayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.abanapps.videoplayer.ui.theme.VideoPlayerTheme
import com.abanapps.videoplayer.ui_layer.Screens.App
import com.abanapps.videoplayer.ui_layer.viewModel.PlayerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  private val viewModel:PlayerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VideoPlayerTheme {

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    App(modifier = Modifier.padding(innerPadding),viewModel)

                }
            }


        }
    }
}



