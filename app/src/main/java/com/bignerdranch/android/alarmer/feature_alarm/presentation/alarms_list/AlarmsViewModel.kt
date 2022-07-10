package com.bignerdranch.android.alarmer.feature_alarm.presentation.alarms_list

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.NotificationCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.bignerdranch.android.alarmer.REPOSITORY
import com.bignerdranch.android.alarmer.feature_alarm.data.database.AlarmRepository
import com.bignerdranch.android.alarmer.feature_alarm.data.database.AppRoomDatabase
import com.bignerdranch.android.alarmer.feature_alarm.data.database.room.repository.Repository
import com.bignerdranch.android.alarmer.feature_alarm.domain.alarm_work.AlarmReciver
import com.bignerdranch.android.alarmer.feature_alarm.domain.models.Alarm
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class AlarmsViewModel (
    application: Application
): AndroidViewModel(application){

    init {
        viewModelScope.launch {
            // Trigger the flow and consume its elements using collect
            REPOSITORY.getAlarms().collect { alarms ->
                state.value = alarms
            }
        }
    }

    val state: MutableState<List<Alarm>> = mutableStateOf(emptyList<Alarm>())

    fun addAlarm(alarm: Alarm, onSuccess: ()-> Unit){
        viewModelScope.launch(Dispatchers.IO) {
            REPOSITORY.insertAlarm(alarm = alarm) {

            }
        }

    }

    fun readAll() = state

    fun deleteAlarm(alarm: Alarm, onSuccess: ()-> Unit){
        viewModelScope.launch(Dispatchers.IO) {
            REPOSITORY.deleteAlarm(alarm = alarm) {
                onSuccess()
            }
        }
    }

    fun updateAlarm(alarm: Alarm, onSuccess: () -> Unit){
        viewModelScope.launch(Dispatchers.IO) {
            REPOSITORY.updateAlarm(alarm = alarm) {
                onSuccess()
            }
        }
    }





}

class AlarmsViewModelFactory(private val application: Application): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(AlarmsViewModel::class.java)){
            return AlarmsViewModel(application=application) as T
        }
        throw IllegalArgumentException("Unknown ViewNodelClass")
    }

}