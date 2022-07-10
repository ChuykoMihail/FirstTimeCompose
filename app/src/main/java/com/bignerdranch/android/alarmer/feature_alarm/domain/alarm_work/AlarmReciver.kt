package com.bignerdranch.android.alarmer.feature_alarm.domain.alarm_work

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.bignerdranch.android.alarmer.feature_alarm.domain.models.Alarm
import java.util.*


const val MONDAY = "MN"
const val TUESDAY = "TS"
const val WEDNESDAY = "WD"
const val THURSDAY = "TH"
const val FRIDAY = "FR"
const val SATURDAY = "ST"
const val SUNDAY = "SN"
const val RECURRING = "RECURRING"
const val TITLE = "TITLE"

class AlarmReciver() : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent?.getAction())) {
            val toastText = String.format("Alarm Reboot");
            Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show();
        }
        else{
            val toastText = String.format("Alarm Received")
            Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show();
            if (!intent!!.getBooleanExtra(RECURRING, false)) {
                startAlarmService(context!!, intent);
            } else {
                if (alarmIsToday(intent)) {
                    startAlarmService(context!!, intent);
                }
            }
        }
    }
    private fun alarmIsToday(intent: Intent): Boolean {
        val calendar: Calendar = Calendar.getInstance()
        calendar.setTimeInMillis(System.currentTimeMillis())
        val today: Int = calendar.get(Calendar.DAY_OF_WEEK)
        when (today) {
            Calendar.MONDAY -> {
                return intent.getBooleanExtra(MONDAY, false)
            }
            Calendar.TUESDAY -> {
                return intent.getBooleanExtra(TUESDAY, false)
            }
            Calendar.WEDNESDAY -> {
                return intent.getBooleanExtra(WEDNESDAY, false)
            }
            Calendar.THURSDAY -> {
                return intent.getBooleanExtra(THURSDAY, false)
            }
            Calendar.FRIDAY -> {
                return intent.getBooleanExtra(FRIDAY, false)
            }
            Calendar.SATURDAY -> {
                return intent.getBooleanExtra(SATURDAY, false)
            }
            Calendar.SUNDAY -> {
                return intent.getBooleanExtra(SUNDAY, false)
            }
        }
        return false
    }
    private fun startAlarmService(context: Context, intent: Intent) {
        val intentService = Intent(context, AlarmService::class.java)
        intentService.putExtra(TITLE, intent.getStringExtra(TITLE))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intentService)
        } else {
            context.startService(intentService)
        }
    }





//    private fun restartNotify() {
//        am = getSystemService(Context.ALARM_SERVICE) as AlarmManager?
//        val intent = Intent(this, TimeNotification::class.java)
//        val pendingIntent = PendingIntent.getBroadcast(
//            this, 0,
//            intent, PendingIntent.FLAG_CANCEL_CURRENT
//        )
//        // На случай, если мы ранее запускали активити, а потом поменяли время,
//// откажемся от уведомления
//        am.cancel(pendingIntent)
//        // Устанавливаем разовое напоминание
//        am.set(AlarmManager.RTC_WAKEUP, stamp.getTime(), pendingIntent)
//    }
}