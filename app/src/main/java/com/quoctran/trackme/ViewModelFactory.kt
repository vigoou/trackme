package com.quoctran.trackme

import android.annotation.SuppressLint
import android.app.Application
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.quoctran.trackme.data.source.WorkoutDataSource
import com.quoctran.trackme.data.source.WorkoutRepository
import com.quoctran.trackme.workout.WorkoutViewModel

class ViewModelFactory private constructor(
    private val workoutRepository: WorkoutRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(WorkoutViewModel::class.java) ->
                    WorkoutViewModel(workoutRepository)

                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T

    companion object {

        @SuppressLint("StaticFieldLeak")
        @Volatile private var INSTANCE: ViewModelFactory? = null

        fun getInstance(application: Application) =
            INSTANCE ?: synchronized(ViewModelFactory::class.java) {
                INSTANCE ?: ViewModelFactory(WorkoutRepository.getInstance(
                    WorkoutDataSource.getInstance(AppExecutors(), database.taskDao())))
                    .also { INSTANCE = it }
            }


        @VisibleForTesting
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}
