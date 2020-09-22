package com.quoctran.trackme.util

import android.content.Context
import android.location.Location
import android.preference.PreferenceManager
import com.quoctran.trackme.R
import com.quoctran.trackme.data.WorkoutPreference
import java.text.DateFormat
import java.util.*

fun Location.getLocationText(): String {
    return "(" + this.latitude + ", " + this.longitude + ")"
}

fun Location.getLocationTitle(context: Context): String{
    return context.getString(
        R.string.location_updated,
        DateFormat.getDateInstance().format(Date()))
}

fun requestingLocationUpdates(): Boolean {
    return
}

fun updateLocationUpdates(Bo){
    return WorkoutPreference.isLocationUpdate!!
}