package com.quoctran.trackme.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "workouts")
data class Workout @JvmOverloads constructor(
    @PrimaryKey @ColumnInfo(name = "_id") var id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "title") var title: String = "",
    @ColumnInfo(name = "timeStart") var timeStart: Long? = null,
    @ColumnInfo(name = "timeEnd") var timeEnd: Long? = null,
    @ColumnInfo(name = "distance") var distance: Float = 0F,
    @ColumnInfo(name = "speed") var speed: Float = 0F,
    @ColumnInfo(name = "imagePath") var imagePath: String? = null
) {
}