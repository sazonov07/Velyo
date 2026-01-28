package com.example.velyo.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.velyo.data.db.entity.WaterLogEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WaterDao {

    @Insert
    suspend fun insert(log: WaterLogEntity)

    @Query("SELECT COALESCE(SUM(amountMl), 0) FROM water_logs WHERE dateKey = :dateKey")
    fun observeTotal(dateKey: String): Flow<Int>
}
