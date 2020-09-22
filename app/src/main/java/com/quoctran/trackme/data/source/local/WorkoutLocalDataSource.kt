package com.quoctran.trackme.data.source.local

import androidx.annotation.VisibleForTesting
import com.quoctran.trackme.util.AppExecutors

class WorkoutLocalDataSource(
    val appExecutors: AppExecutors,
    val workoutDao: WorkoutDao
) {

    companion object {
        private var INSTANCE: WorkoutLocalDataSource? = null

        @JvmStatic
        fun getInstance(
            appExecutors: AppExecutors,
            workoutDao: WorkoutDao
        ): WorkoutLocalDataSource {
            if (INSTANCE == null) {
                synchronized(WorkoutLocalDataSource::javaClass) {
                    INSTANCE = WorkoutLocalDataSource(appExecutors, workoutDao)
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