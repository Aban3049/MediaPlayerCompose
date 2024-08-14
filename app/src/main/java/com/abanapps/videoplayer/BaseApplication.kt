package com.abanapps.videoplayer

import android.app.Application
import com.abanapps.videoplayer.data_layer.roomDatabase.databaseBuilder
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication:Application() {

}