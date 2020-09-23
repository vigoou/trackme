package com.quoctran.trackme.data.source.local

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.quoctran.trackme.data.Workout

@Database(entities = arrayOf(Workout::class), version = 1)
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