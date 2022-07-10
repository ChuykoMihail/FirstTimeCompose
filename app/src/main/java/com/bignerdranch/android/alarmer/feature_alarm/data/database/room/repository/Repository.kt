package com.bignerdranch.android.alarmer.feature_alarm.data.database.room.repository

import com.bignerdranch.android.alarmer.feature_alarm.data.database.room.dao.AlarmDao
import com.bignerdranch.android.alarmer.feature_alarm.domain.models.Alarm
import com.bignerdranch.android.alarmer.feature_alarm.data.database.AlarmRepository
import kotlinx.coroutines.flow.Flow

class Repository(
    private val dao: AlarmDao
): AlarmRepository {

    override fun getAlarms(): Flow<List<Alarm>> {
        return  dao.getAlarms()
    }

    override suspend fun getAlarmById(id: Int, onSuccess: () -> Unit): Alarm? {
        return dao.getAlarmById(id)
    }

    override suspend fun insertAlarm(alarm: Alarm, onSuccess: () -> Unit) {
        return dao.insertAlarm(alarm)
    }

    override suspend fun updateAlarm(alarm: Alarm, onSuccess: () -> Unit) {
        return dao.updateAlarm(alarm)
    }

    override suspend fun deleteAlarm(alarm: Alarm, onSuccess: () -> Unit) {
        return dao.deleteAlarm(alarm)
    }


}