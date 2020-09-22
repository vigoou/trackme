package com.quoctran.trackme.workout

import androidx.lifecycle.ViewModel
import com.quoctran.trackme.data.source.WorkoutRepository

class WorkoutViewModel(
private val workoutRepository : WorkoutRepository
) :ViewModel(){
}