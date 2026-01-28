package com.example.velyo.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "water_logs")
data class WaterLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val dateKey: String,      // например "2026-01-28"
    val amountMl: Int,
    val timestamp: Long = System.currentTimeMillis()
)
