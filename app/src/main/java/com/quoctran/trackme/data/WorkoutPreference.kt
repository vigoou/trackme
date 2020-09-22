package com.quoctran.trackme.data

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

object WorkoutPreference {
    private lateinit var sharedPreferences: SharedPreferences

    private const val PREF_NAME = "workout_preference"

    fun init(app: Application) {
        sharedPreferences = app.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    private inline fun <reified T : Any> put(key: KEY, t: T?) {
        sharedPreferences.edit().apply {
            when (t) {
                is Boolean -> putBoolean(key.value , t)
                else -> null
            }?.apply()
        }
    }

    @Suppress("IMPLICIT_CAST_TO_ANY")
    private inline fun <reified T : Any> get(key: KEY, default: T?): T? =
        sharedPreferences.let {
            when (default) {
                is Boolean -> it.getBoolean(key.value, default)
                is String -> it.getString(key.value, default)
                is Float -> it.getFloat(key.value, default)
                is Int -> it.getInt(key.value, default)
                is Long -> it.getLong(key.value, default)
                else -> null
            }
        } as T?

    var isLocationUpdate : Boolean?
        get() = this.get(KEY.IS_LOCATION_UPDATE, false)
        set(value) = put(KEY.IS_LOCATION_UPDATE, value)

    private enum class KEY(val value: String) {
        IS_LOCATION_UPDATE("is_location_update"),
    }
}


