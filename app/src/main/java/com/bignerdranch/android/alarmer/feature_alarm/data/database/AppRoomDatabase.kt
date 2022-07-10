package com.bignerdranch.android.alarmer.feature_alarm.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bignerdranch.android.alarmer.feature_alarm.data.database.room.dao.AlarmDao
import com.bignerdranch.android.alarmer.feature_alarm.domain.models.Alarm

@Database(entities = [Alarm::class], version = 2)
abstract class AppRoomDatabase : RoomDatabase() {

    abstract fun getRoomDao(): AlarmDao

    companion object{
        @Volatile
        private var INSTANCE: AppRoomDatabase? = null

        fun getInstance(context: Context): AppRoomDatabase{
            return if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context,
                    AppRoomDatabase::class.java,
                    "first_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE as AppRoomDatabase
            } else{
                return INSTANCE as AppRoomDatabase
            }
        }
    }

}