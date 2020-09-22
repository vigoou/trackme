package com.quoctran.trackme.data.source

import com.quoctran.trackme.data.source.local.WorkoutLocalDataSource

class WorkoutRepository(
    val workoutLocalDataSource: WorkoutLocalDataSource
) : WorkoutDataSource{

    companion object {

        private var INSTANCE: WorkoutRepository? = null

        @JvmStatic fun getInstance(workoutLocalDataSource: WorkoutLocalDataSource) =
            INSTANCE ?: synchronized(WorkoutRepository::class.java) {
                INSTANCE ?: WorkoutRepository(workoutLocalDataSource)
                    .also { INSTANCE = it }
            }
        @JvmStatic fun destroyInstance() {
            INSTANCE = null
        }
    }
}