package com.bignerdranch.android.alarmer.feature_alarm.presentation.alarms_list.components

import android.app.Application
import android.app.TimePickerDialog
import android.os.Build
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.ui.unit.dp
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bignerdranch.android.alarmer.feature_alarm.domain.models.Alarm
import com.bignerdranch.android.alarmer.feature_alarm.presentation.alarms_list.AlarmsViewModel
import com.bignerdranch.android.alarmer.feature_alarm.presentation.alarms_list.AlarmsViewModelFactory
import java.util.*

enum class Days(val nameToShow: String) {
    Monday("MN"), Tuesday("TS"), Wednesday("WD"), Thursday("TH"),
    Friday("FR"), Saturday("ST"), Sunday("SN"),
}






@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FullAlarmElement(
    id:Int,
    onDeleteAlarm:()->Unit,
)
{

    val context = LocalContext.current
    val viewModel: AlarmsViewModel =
        viewModel(factory = AlarmsViewModelFactory(context.applicationContext as Application))


    val localState = remember {
        mutableStateOf(viewModel.state)
    }
    val alarm = remember {
        mutableStateOf(localState.value.value[localState.value.value.indexOfFirst { it.id == id }])
    }



    Card(modifier = Modifier
        .padding(horizontal = 8.dp, vertical = 8.dp)
        .fillMaxWidth()
        .combinedClickable(true, onLongClick = {

            alarm.value.cancelAlarm(context = context)
            onDeleteAlarm()
        }, onClick = {}
        ),
        elevation = 2.dp,
        backgroundColor = Color.White,
        shape = RoundedCornerShape(corner = CornerSize(16.dp))

    ) {
        Column() {
            Row(modifier = Modifier.fillMaxWidth(0.75f)) {
                Column() {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                            .fillMaxWidth(1f)
                            .padding(start = 8.dp)
                    ) {
                        Text(text = alarm.value.time, fontSize = 22.sp)
                        Switch(
                            checked = alarm.value.isActive, onCheckedChange =
                            {
                                val nValue = alarm.value.copy(isActive = it)
                                alarm.value = nValue
                                if(alarm.value.isActive){
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                        alarm.value.createNotificationChannel(context = context)
                                    }
                                } else{
                                    alarm.value.cancelAlarm(context = context)
                                }

                                viewModel.updateAlarm(nValue) {

                                }
                            },
                            Modifier
                                .scale(1f)
                                .padding(end = 8.dp, top = 4.dp)
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth(1f)
                            .padding(end = 8.dp),
                    ) {
                        DaysOfWeek(isEveryday = false, alarm.value)
                    }
//                AlarmDetails(daysChecked = utilDays(alarm.days), onDatePick = , onTimePicked = )
                }
            }
            AlarmDetails(
                alarm = alarm.value,
                onTimePicked = {
                        str -> alarm.value = alarm.value.copy(time = str)
                        viewModel.updateAlarm(alarm.value, {})
                               },
                {alarm -> viewModel.updateAlarm(alarm, {}) }
            )
        }
    }
}



@Composable
fun AlarmElement(
    time: String = "00:00",
    isActive: Boolean = true,
    days: List<Days> = listOf(Days.Monday),
    onChangeActive: ()-> Unit,
) {

}



@Composable
fun DaysOfWeek(
    isEveryday: Boolean,
    alarm: Alarm
) {
    Row() {
        if (isEveryday) Text(
            text = "Every day",
            fontSize = 14.sp,
            modifier = Modifier.padding(start = 8.dp)
        )
        else {
            var str = ""
            if(alarm.isMN)str += "MN; "
            if(alarm.isTS)str += "TS; "
            if(alarm.isWD)str += "WD; "
            if(alarm.isTH)str += "TH; "
            if(alarm.isFR)str += "FR; "
            if(alarm.isST)str += "ST; "
            if(alarm.isSN)str += "SN; "
            Text(text = str, fontSize = 14.sp, modifier = Modifier.padding(start = 8.dp))
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun defPreview() {
    Card(modifier = Modifier
        .padding(horizontal = 8.dp, vertical = 8.dp)
        .fillMaxWidth()
        .combinedClickable(true, onLongClick = {

        }, onClick = {}
        ),
        elevation = 2.dp,
        backgroundColor = Color.White,
        shape = RoundedCornerShape(corner = CornerSize(16.dp))

    ) {
        Column() {
            AlarmElement(
                onChangeActive = {})
//            AlarmDetails(, onDatePick = {}, onTimePicked = {})
        }
    }

}

