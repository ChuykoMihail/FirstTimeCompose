package com.bignerdranch.android.alarmer.feature_alarm.presentation.alarms_list.components

import android.app.TimePickerDialog
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bignerdranch.android.alarmer.feature_alarm.domain.models.Alarm
import java.util.*

@Composable
fun AlarmDetails(
    alarm: Alarm,
    onTimePicked: (String) -> Unit,
    onAlarmChanged: (Alarm) -> Unit

){


    val mContext = LocalContext.current

    val localState = remember {
        mutableStateOf(alarm)
    }

    val mCalendar = Calendar.getInstance()
    val mHour = mCalendar[Calendar.HOUR_OF_DAY]
    val mMinute = mCalendar[Calendar.MINUTE]

    val mTimePickerDialog = TimePickerDialog(
        mContext,
        {_, mHour : Int, mMinute: Int ->
            onTimePicked("$mHour:$mMinute")
        }, mHour, mMinute, false
    )

    Column() {
        Row(modifier = Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
            DefaultRadioButton(text = "MN", isChecked = localState.value.isMN, onCheckChange = {
                localState.value = localState.value.copy(isMN = !localState.value.isMN)
                onAlarmChanged(localState.value)
            })
            DefaultRadioButton(text = "TS", isChecked = localState.value.isTS, onCheckChange = {
                localState.value = localState.value.copy(isTS = !localState.value.isTS)
                onAlarmChanged(localState.value)
            })
            DefaultRadioButton(text = "WD", isChecked = localState.value.isWD, onCheckChange = {
                localState.value = localState.value.copy(isWD = !localState.value.isWD)
                onAlarmChanged(localState.value)
            })
            DefaultRadioButton(text = "TH", isChecked = localState.value.isTH, onCheckChange = {
                localState.value = localState.value.copy(isTH = !localState.value.isTH)
                onAlarmChanged(localState.value)
            })
        }
        Row(modifier = Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
            DefaultRadioButton(text = "FR", isChecked = localState.value.isFR, onCheckChange = {
                localState.value = localState.value.copy(isFR = !localState.value.isFR)
                onAlarmChanged(localState.value)
            })
            DefaultRadioButton(text = "ST", isChecked = localState.value.isST, onCheckChange = {
                localState.value = localState.value.copy(isST = !localState.value.isST)
                onAlarmChanged(localState.value)
            })
            DefaultRadioButton(text = "SN", isChecked = localState.value.isSN, onCheckChange = {
                localState.value = localState.value.copy(isSN = !localState.value.isSN)
                onAlarmChanged(localState.value)
            })
        }
        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
           Button(onClick = {
               mTimePickerDialog.show()
           }, modifier = Modifier.padding(6.dp),
           ) {
                Text(text = "Выбрать время")
           }
        }
    }
}

@Composable
fun DefaultRadioButton(
    text: String,
    isChecked: Boolean,
    onCheckChange: () -> Unit,
    modifier: Modifier = Modifier
){
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically){
        RadioButton(selected = isChecked, onClick = onCheckChange )
        Spacer(modifier = Modifier.width(6.dp))
        Text(text = text)
        Spacer(modifier = Modifier.width(6.dp))
    }
}

//@Preview
//@Composable
//fun prev(){
//    AlarmDetails(
//        daysChecked = listOf(Days.Sunday, Days.Saturday, Days.Friday, Days.Thursday, Days.Wednesday, Days.Tuesday, Days.Monday),
//        onDatePick = {},
//        onTimePicked = {}
//    )
//}