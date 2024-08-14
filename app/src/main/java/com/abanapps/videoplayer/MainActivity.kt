package com.abanapps.videoplayer

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.abanapps.videoplayer.data_layer.Repo.RoomRepo
import com.abanapps.videoplayer.data_layer.Repo.VideoAppRepoImpl
import com.abanapps.videoplayer.data_layer.roomDatabase.databaseBuilder
import com.abanapps.videoplayer.data_layer.viewModel.MusicViewModelFactory
import com.abanapps.videoplayer.data_layer.viewModel.RoomViewModel
import com.abanapps.videoplayer.domain_layer.Repo.VideoAppRepo
import com.abanapps.videoplayer.ui.theme.VideoPlayerTheme
import com.abanapps.videoplayer.ui_layer.Screens.App
import com.abanapps.videoplayer.ui_layer.viewModel.PlayerViewModel
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

}



