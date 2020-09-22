package com.quoctran.trackme

import android.telecom.Call
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import java.util.stream.Collector.of

fun <T : ViewModel> AppCompatActivity.obtainViewModel(viewModelClass : Class<T>) : T =
    ViewModelProviders.of(this, ViewModelFactory.getInstance(application)).get(viewModelClass)

