package com.example.velyo.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.velyo.ui.util.animateFloat
import com.example.velyo.ui.util.animateInt
import com.example.velyo.vm.WaterViewModel

@Composable
fun WaterScreen(vm: WaterViewModel) {
    val total by vm.todayTotal.collectAsState()
    val state by vm.uiState.collectAsState()

    val animatedProgress = animateFloat(state.progress)
    val animatedTotal = animateInt(total)
    val animatedTotal1 = animateInt(state.remaining)



    val indicatorColor =
        if (state.isGoalReached) MaterialTheme.colorScheme.secondary
        else MaterialTheme.colorScheme.primary

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Velyo", style = MaterialTheme.typography.titleLarge)
        Row(Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier.size(150.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    progress = { animatedProgress },
                    modifier = Modifier.fillMaxSize(),
                    strokeWidth = 10.dp,
                    color = indicatorColor
                )

                Text(
                    text = if (state.isGoalReached) "Done 🎉" else "${(animateFloat(state.progress) * 100).toInt()}%",
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )
            }
            Column (modifier = Modifier.fillMaxWidth().padding(6.dp), horizontalAlignment = Alignment.CenterHorizontally){
                Text(
                    text = if (state.isGoalReached) "Goal reached!" else "${animatedTotal1.toString()} ml left",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    buildAnnotatedString {
                        withStyle(SpanStyle(fontSize = 14.sp)) { append("Today: ") }
                        withStyle(SpanStyle(
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary)
                        ) {
                            append(animatedTotal.toString())
                        }
                        withStyle(SpanStyle(fontSize = 14.sp)) { append(" ml") }
                    },
                    style = MaterialTheme.typography.bodyLarge
                )
            }

        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedButton(onClick = { vm.addWater(150) }, modifier = Modifier.weight(1f)) { Text("+250") }
            OutlinedButton(onClick = { vm.addWater(250) }, modifier = Modifier.weight(1f)) { Text("+300") }
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

