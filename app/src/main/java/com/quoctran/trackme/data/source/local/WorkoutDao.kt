package com.quoctran.trackme.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import com.quoctran.trackme.data.Workout

@Dao
interface WorkoutDao {

    /**
     * create new workout
     * */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createWorkout(workout: Workout)

    /**
     * update time, speed, distance
     *  */
    @Update fun updateWorkout(workout: Workout): Int
}