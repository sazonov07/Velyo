package com.example.velyo.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.velyo.vm.WaterViewModel

@Composable
fun WaterScreen(vm: WaterViewModel) {
    val total by vm.todayTotal.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Velyo", style = MaterialTheme.typography.titleLarge)
        Text("Today: $total ml", style = MaterialTheme.typography.titleMedium)

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedButton(onClick = { vm.addWater(150) }, modifier = Modifier.weight(1f)) { Text("+150") }
            OutlinedButton(onClick = { vm.addWater(250) }, modifier = Modifier.weight(1f)) { Text("+250") }
            Button(onClick = { vm.addWater(500) }, modifier = Modifier.weight(1f)) { Text("+500") }
        }
        WaterHistory(vm)
    }

}

@Composable
fun WaterHistory(vm: WaterViewModel) {
    val groups by vm.history.collectAsState()
    val scroll = rememberScrollState()

    Text("History", style = MaterialTheme.typography.titleMedium)
    Column(modifier = Modifier
        .verticalScroll(scroll),
        verticalArrangement = Arrangement.spacedBy(8.dp))
    {
        groups.forEach { day ->
            Card(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 0.dp, end = 0.dp, top = 0.dp, bottom = 8.dp)) {
                Column(modifier = Modifier
                    .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp))
                {
                    Text(
                        "${day.dateKey} • ${day.totalMl} ml",
                        style = MaterialTheme.typography.titleSmall
                    )

                    day.logs.forEach { log ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 8.dp, end = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(log.timeText)
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text("+${log.amountMl} ml")
                                IconButton(onClick = { vm.deleteWater(log.id) }) {
                                    Icon(Icons.Filled.Delete, contentDescription = "Delete")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

