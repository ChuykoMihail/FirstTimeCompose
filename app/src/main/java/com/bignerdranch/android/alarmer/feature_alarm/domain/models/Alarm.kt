package com.bignerdranch.android.alarmer.feature_alarm.domain.models

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bignerdranch.android.alarmer.feature_alarm.domain.alarm_work.AlarmReciver
import com.bignerdranch.android.alarmer.feature_alarm.presentation.alarms_list.components.Days
import java.util.*

@Entity
data class Alarm(
    @PrimaryKey (autoGenerate = true)
    val id: Int = 0,
    val time: String = "00:00",
    val isEveryDay: Boolean = false,
    val isActive: Boolean = true,
    val isMN: Boolean = true,
    val isTS: Boolean = false,
    val isWD: Boolean = false,
    val isTH: Boolean = false,
    val isFR: Boolean = false,
    val isST: Boolean = false,
    val isSN: Boolean = false
){
    @SuppressLint("DefaultLocale")
    fun cancelAlarm(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReciver::class.java)
        val alarmPendingIntent = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_IMMUTABLE)
        alarmManager.cancel(alarmPendingIntent)
        val toastText = java.lang.String.format(
            "Alarm cancelled for %02d:%02d with id %d",
            TimeToHours(time),
            TimeToMinutes(time),
            id
        )
        Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()
        Log.i("cancel", toastText)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createNotificationChannel(context: Context){
        val alarmManager = context.applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent : Intent = Intent(context, AlarmReciver::class.java)
        intent.putExtra("MN", isMN)
        intent.putExtra("TS", isTS)
        intent.putExtra("WD", isWD)
        intent.putExtra("TG", isTH)
        intent.putExtra("FR", isST)
        intent.putExtra("ST", isST)
        intent.putExtra("SN", isSN)

        val pendingIntent = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_IMMUTABLE)

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.set(Calendar.HOUR_OF_DAY, TimeToHours(time))
        calendar.set(Calendar.HOUR_OF_DAY, TimeToMinutes(time))
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
        }

        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            calendar.getTimeInMillis(),
            pendingIntent
        );
    }

    fun TimeToHours(str: String) : Int =
        str.split(":").first().toInt()

    fun TimeToMinutes(str: String): Int =
        str.split(":").last().toInt()
}
