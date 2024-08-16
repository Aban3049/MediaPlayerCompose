package com.abanapps.videoplayer.data_layer.DI

import android.app.Application
import com.abanapps.videoplayer.data_layer.Repo.AppRepoImpl
import com.abanapps.videoplayer.domain_layer.Repo.AppRepo
import com.abanapps.videoplayer.ui_layer.viewModel.PlayerViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object DIModule {

    @Provides
    fun repoProvider(): AppRepo {
        return AppRepoImpl()
    }

    @Provides
    fun providePlayerViewModel(
        repo: AppRepo,
        application: Application
    ): PlayerViewModel {
        return PlayerViewModel(repo, application)
    }


}