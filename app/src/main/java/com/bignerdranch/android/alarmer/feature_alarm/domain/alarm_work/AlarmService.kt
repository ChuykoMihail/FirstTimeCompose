package com.bignerdranch.android.alarmer.feature_alarm.domain.alarm_work

import android.R
import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.os.IBinder
import android.os.Vibrator
import androidx.core.app.NotificationCompat
import com.bignerdranch.android.alarmer.AlarmApp
import com.bignerdranch.android.alarmer.MainActivity


class AlarmService(): Service() {

    lateinit var mediaPlayer: MediaPlayer
    lateinit var vibrator: Vibrator

    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer.create(this, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM))
        mediaPlayer.setLooping(true)
        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)
        val alarmTitle = String.format("%s Alarm", intent.getStringExtra(TITLE))
        val notification: Notification = NotificationCompat.Builder(this, "ALARM_SERVICE_CHANNEL")
            .setContentTitle(alarmTitle)
            .setContentText("Ring Ring .. Ring Ring")
            .setSmallIcon(R.drawable.alert_dark_frame)
            .setContentIntent(pendingIntent)
            .addAction(R.drawable.btn_dialog, "Стоп", pendingIntent)
            .build()
        mediaPlayer.start()
        val pattern = longArrayOf(0, 100, 1000)
        vibrator.vibrate(pattern, 0)
        startForeground(1, notification)
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.stop()
        vibrator.cancel()
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }
}