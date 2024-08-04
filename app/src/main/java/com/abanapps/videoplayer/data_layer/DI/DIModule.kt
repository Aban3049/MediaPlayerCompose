package com.abanapps.videoplayer.data_layer.DI

import com.abanapps.videoplayer.data_layer.Repo.VideoAppRepoImpl
import com.abanapps.videoplayer.domain_layer.Repo.VideoAppRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object DIModule {

    @Provides
    fun repoProvider():VideoAppRepo{
        return VideoAppRepoImpl()
    }

}