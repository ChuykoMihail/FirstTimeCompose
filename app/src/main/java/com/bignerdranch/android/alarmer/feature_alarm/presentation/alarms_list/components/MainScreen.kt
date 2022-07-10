package com.bignerdranch.android.alarmer.feature_alarm.presentation.alarms_list.components

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bignerdranch.android.alarmer.feature_alarm.presentation.alarms_list.AlarmsViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bignerdranch.android.alarmer.feature_alarm.domain.models.Alarm
import androidx.compose.runtime.getValue
import com.bignerdranch.android.alarmer.feature_alarm.presentation.alarms_list.AlarmsViewModelFactory


@Composable
fun MainScreen() {

    val context = LocalContext.current

    val viewModel: AlarmsViewModel =
        viewModel(factory = AlarmsViewModelFactory(context.applicationContext as Application))

    val localState = remember {
        viewModel.state
    }

    val scaffoldState = rememberScaffoldState()

    Scaffold(
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.addAlarm(alarm = Alarm(), {})
                },
                backgroundColor = Color.Red
            ) {
                Icon(
                    Icons.Filled.Add, ""
                )
            }
        },
        scaffoldState = scaffoldState,
        content = {
            Column(){
                Text(text = "VK Alarms", fontSize = 32.sp, modifier = Modifier.padding(16.dp))
                LazyColumn(contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)) {
                    items(localState.value) { alarm ->
                        FullAlarmElement(alarm.id) {
                            viewModel.deleteAlarm(alarm = alarm, {})
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }

        }
    )

}