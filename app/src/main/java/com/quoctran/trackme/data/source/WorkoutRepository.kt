package com.quoctran.trackme.data.source

class WorkoutRepository(
    val workoutDataSource: WorkoutDataSource
) : WorkoutDataSource{

    companion object {

        private var INSTANCE: WorkoutRepository? = null

        @JvmStatic fun getInstance(workoutDataSource: WorkoutDataSource) =
            INSTANCE ?: synchronized(WorkoutRepository::class.java) {
                INSTANCE ?: WorkoutRepository(workoutDataSource)
                    .also { INSTANCE = it }
            }
        @JvmStatic fun destroyInstance() {
            INSTANCE = null
        }
    }
}