package com.quoctran.trackme.data.source

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.VisibleForTesting

interface WorkoutDataSource {

    companion object {
        private var INSTANCE: WorkoutDataSource? = null

        @JvmStatic
        fun getInstance(appExecutors: AppExecutors, tasksDao: TasksDao): TasksLocalDataSource {
            if (INSTANCE == null) {
                synchronized(TasksLocalDataSource::javaClass) {
                    INSTANCE = TasksLocalDataSource(appExecutors, tasksDao)
                }
            }
            return INSTANCE!!
        }

        @VisibleForTesting
        fun clearInstance() {
            INSTANCE = null
        }
    }
}