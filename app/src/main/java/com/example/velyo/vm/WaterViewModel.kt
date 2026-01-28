package com.example.velyo.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.velyo.data.db.dao.WaterDao
import com.example.velyo.data.db.entity.WaterLogEntity
import com.example.velyo.util.DateUtil
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class WaterViewModel(private val dao: WaterDao) : ViewModel() {

    private val todayKey = DateUtil.todayKey()

    val todayTotal = dao.observeTotal(todayKey)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), 0)

    fun addWater(amountMl: Int) {
        viewModelScope.launch {
            dao.insert(
                WaterLogEntity(dateKey = todayKey, amountMl = amountMl)
            )
        }
    }
}
