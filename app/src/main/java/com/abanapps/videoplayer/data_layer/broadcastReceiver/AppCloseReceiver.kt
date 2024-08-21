package com.abanapps.videoplayer.data_layer.broadcastReceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.abanapps.videoplayer.data_layer.service.MusicService

class AppCloseReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "com.yourapp.APP_CLOSED") {
            val serviceIntent = Intent(context, MusicService::class.java)
            context.stopService(serviceIntent)
        }
    }
}