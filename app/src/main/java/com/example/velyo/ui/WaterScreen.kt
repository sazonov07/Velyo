package com.example.velyo.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.velyo.vm.WaterViewModel

@Composable
fun WaterScreen(vm: WaterViewModel) {
    val total by vm.todayTotal.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Velyo", style = MaterialTheme.typography.titleLarge)
        Text("Today: $total ml", style = MaterialTheme.typography.titleMedium)

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedButton(onClick = { vm.addWater(150) }, modifier = Modifier.weight(1f)) { Text("+150") }
            OutlinedButton(onClick = { vm.addWater(250) }, modifier = Modifier.weight(1f)) { Text("+250") }
            Button(onClick = { vm.addWater(500) }, modifier = Modifier.weight(1f)) { Text("+500") }
        }
    }
}
