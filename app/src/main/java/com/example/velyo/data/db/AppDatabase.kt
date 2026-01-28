package com.example.velyo.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.velyo.data.db.dao.WaterDao
import com.example.velyo.data.db.entity.WaterLogEntity

@Database(entities = [WaterLogEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun waterDao(): WaterDao
}
