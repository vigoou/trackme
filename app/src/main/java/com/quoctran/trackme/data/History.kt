package com.quoctran.trackme.data;

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "history")
data class History @JvmOverloads constructor(
    @PrimaryKey @ColumnInfo(name = "_id") var id: String = UUID.randomUUID().toString()
) {
}