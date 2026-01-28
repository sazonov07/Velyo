package com.example.velyo.util

import java.time.LocalDate

object DateUtil {
    fun todayKey(): String = LocalDate.now().toString()
}
