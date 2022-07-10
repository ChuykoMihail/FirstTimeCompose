package com.bignerdranch.android.alarmer

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.bignerdranch.android.alarmer.feature_alarm.data.database.AppRoomDatabase
import com.bignerdranch.android.alarmer.feature_alarm.data.database.room.repository.Repository
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class AlarmApp : Application(){
    val CHANNEL_ID = "ALARM_SERVICE_CHANNEL"
    override fun onCreate() {
        super.onCreate()
        val dao = AppRoomDatabase.getInstance(applicationContext).getRoomDao()
        REPOSITORY = Repository(dao)
        createNotificationChannnel()
    }

    private fun createNotificationChannnel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "Alarm Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(
                NotificationManager::class.java
            )
            manager.createNotificationChannel(serviceChannel)
        }
    }
}