package com.quoctran.trackme

import androidx.multidex.MultiDexApplication
import com.quoctran.trackme.data.WorkoutPreference

class MyApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        WorkoutPreference.init(this@MyApplication)

    }
}