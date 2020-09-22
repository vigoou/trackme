package com.quoctran.trackme.workout

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.quoctran.trackme.R
import com.quoctran.trackme.obtainViewModel
import com.quoctran.trackme.replaceFragmentInActivity

class WorkoutActivity : AppCompatActivity() {

    private lateinit var workoutViewModel: WorkoutViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout)
        workoutViewModel = obtainViewModel()

        replaceFragmentInActivity(findOrCreateViewFragment(), R.id.fl_container)
    }

    private fun findOrCreateViewFragment() =
        supportFragmentManager.findFragmentById(R.id.fl_container) ?: WorkoutFragment.newInstance()

    private fun obtainViewModel(): WorkoutViewModel = obtainViewModel(WorkoutViewModel::class.java)
}