package com.sriram.smartdriveai.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trips")
data class TripEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val timestamp: Long,

    val durationMillis: Long,

    val rapidAccelerations: Int,

    val harshBrakes: Int,

    val safetyScore: Int
)