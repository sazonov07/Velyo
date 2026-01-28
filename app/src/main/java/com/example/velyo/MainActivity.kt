package com.example.velyo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.room.Room
import com.example.velyo.data.db.AppDatabase
import com.example.velyo.ui.WaterScreen
import com.example.velyo.ui.theme.VelyoTheme
import com.example.velyo.vm.WaterViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "velyo.db"
        ).build()

        val vm = WaterViewModel(db.waterDao())

        setContent {
            VelyoTheme {
                WaterScreen(vm)
            }
        }
    }
}
