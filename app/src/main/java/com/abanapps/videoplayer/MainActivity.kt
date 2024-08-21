package com.abanapps.videoplayer

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.abanapps.videoplayer.data_layer.Repo.RoomRepo
import com.abanapps.videoplayer.data_layer.roomDatabase.databaseBuilder
import com.abanapps.videoplayer.data_layer.viewModel.MusicViewModelFactory
import com.abanapps.videoplayer.data_layer.viewModel.RoomViewModel
import com.abanapps.videoplayer.ui.theme.VideoPlayerTheme
import com.abanapps.videoplayer.ui_layer.Screens.App
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var repository: RoomRepo
    private lateinit var factory: MusicViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VideoPlayerTheme {
                val repository = RoomRepo(databaseBuilder(applicationContext).songsDao())
                val factory = MusicViewModelFactory(repository)
                val dao = databaseBuilder(applicationContext).songsDao()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    val viewModel: RoomViewModel = viewModel(factory = factory)

                    App(modifier = Modifier.padding(innerPadding), roomViewModel = viewModel)

                }
            }


        }
    }

    override fun onDestroy() {
        super.onDestroy()

        if (isTaskRoot) {
            val intent = Intent("com.yourapp.APP_CLOSED")
            sendBroadcast(intent)
        }
    }

}



