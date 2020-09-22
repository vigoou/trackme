package com.quoctran.trackme.data.source.local

import android.content.Context
import androidx.room.DatabaseConfiguration
import androidx.room.InvalidationTracker
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper

abstract class WorkoutDatabase : RoomDatabase() {

    abstract fun workoutDao() : WorkoutDao
    abstract fun historyDao() : HistoryDao
    companion object {

        private var INSTANCE: WorkoutDatabase? = null

        private val lock = Any()

        fun getInstance(context: Context): WorkoutDatabase {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        WorkoutDatabase::class.java, "workout.db")
                        .build()
                }
                return INSTANCE!!
            }
        }
    }
}