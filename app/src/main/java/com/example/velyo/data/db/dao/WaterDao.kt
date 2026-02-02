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

    @Query("""
    SELECT * FROM water_logs
    ORDER BY dateKey DESC, timestamp DESC
""")
    fun observeAllLogs(): Flow<List<WaterLogEntity>>

    @Query("DELETE FROM water_logs WHERE id = :id")
    suspend fun deleteById(id: Long)
}
