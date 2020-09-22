package com.quoctran.trackme

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.quoctran.trackme.service.WorkoutService

class MainActivity : AppCompatActivity() {

    private val mService: WorkoutService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}