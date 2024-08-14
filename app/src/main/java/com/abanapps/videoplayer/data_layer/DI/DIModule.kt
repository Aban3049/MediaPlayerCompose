package com.abanapps.videoplayer.data_layer.DI

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.abanapps.videoplayer.data_layer.Repo.RoomRepo
import com.abanapps.videoplayer.data_layer.Repo.VideoAppRepoImpl
import com.abanapps.videoplayer.data_layer.roomDatabase.SongsDao
import com.abanapps.videoplayer.data_layer.roomDatabase.SongsDatabase
import com.abanapps.videoplayer.domain_layer.Repo.VideoAppRepo
import com.abanapps.videoplayer.ui_layer.viewModel.PlayerViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DIModule {

    @Provides
    fun repoProvider(): VideoAppRepo {
        return VideoAppRepoImpl()
    }

    @Provides
    fun providePlayerViewModel(
        repo: VideoAppRepo,
        application: Application
    ): PlayerViewModel {
        return PlayerViewModel(repo, application)
    }


}