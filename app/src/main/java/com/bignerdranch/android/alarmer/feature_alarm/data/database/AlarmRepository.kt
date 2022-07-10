package com.bignerdranch.android.alarmer.feature_alarm.data.database


import com.bignerdranch.android.alarmer.feature_alarm.domain.models.Alarm
import kotlinx.coroutines.flow.Flow

interface AlarmRepository {

    fun getAlarms(): Flow<List<Alarm>>

    suspend fun getAlarmById(id: Int, onSuccess: () -> Unit): Alarm?

    suspend fun insertAlarm(alarm: Alarm, onSuccess: () -> Unit)

    suspend fun updateAlarm(alarm: Alarm, onSuccess: () -> Unit)

    suspend fun deleteAlarm(alarm: Alarm, onSuccess: () -> Unit)
}