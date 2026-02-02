package com.example.velyo.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.velyo.data.db.AppDatabase
import com.example.velyo.data.db.dao.WaterDao
import com.example.velyo.data.db.entity.WaterLogEntity
import com.example.velyo.util.DateUtil
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

data class WaterLogUi(
    val id: Long,
    val amountMl: Int,
    val timeText: String
)

data class WaterDayGroupUi(
    val dateKey: String,
    val totalMl: Int,
    val logs: List<WaterLogUi>
)

data class WaterUiState(
    val total: Int = 0,
    val goal: Int = 2000,
) {
    val progress: Float
        get() = (total.toFloat() / goal).coerceIn(0f, 1f)

    val remaining: Int
        get() = (goal - total).coerceAtLeast(0)

    val isGoalReached: Boolean
        get() = total >= goal
}



class WaterViewModel(application: Application) : AndroidViewModel(application) {

    private val db = Room.databaseBuilder(
        application,
        AppDatabase::class.java,
        "velyo.db"
    ).build()

    private val dao = db.waterDao()
    private val todayKey = DateUtil.todayKey()

    val todayTotal = dao.observeTotal(todayKey)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), 0)

    val uiState = todayTotal
        .map { total ->
            WaterUiState(total = total)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), WaterUiState())


    fun addWater(amountMl: Int) {
        viewModelScope.launch {
            dao.insert(
                WaterLogEntity(dateKey = todayKey, amountMl = amountMl)
            )
        }
    }

    fun deleteWater(id: Long) {
        viewModelScope.launch {
            dao.deleteById(id)
        }
    }


    private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    val history = dao.observeAllLogs()
        .map { logs ->
            logs
                .groupBy { it.dateKey } // группируем по "2026-02-02"
                .toSortedMap(compareByDescending { it }) // сортируем дни по убыванию
                .map { (dateKey, dayLogs) ->
                    val uiLogs = dayLogs.map { e ->
                        val time = Instant.ofEpochMilli(e.timestamp)
                            .atZone(ZoneId.systemDefault())
                            .toLocalTime()
                            .format(timeFormatter)

                        WaterLogUi(
                            id = e.id,
                            amountMl = e.amountMl,
                            timeText = time
                        )
                    }

                    WaterDayGroupUi(
                        dateKey = dateKey,
                        totalMl = dayLogs.sumOf { it.amountMl },
                        logs = uiLogs
                    )
                }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())
}
