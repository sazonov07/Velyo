package com.example.velyo.ui.util

import androidx.compose.animation.core.*
import androidx.compose.runtime.*

@Composable
fun animateFloat(
    target: Float,
    duration: Int = 400,
    delay: Int = 0
): Float {
    val value by animateFloatAsState(
        targetValue = target,
        animationSpec = tween(1500),
        label = "animateFloat"
    )
    return value
}

@Composable
fun animateInt(
    target: Int,
    duration: Int = 400,
    delay: Int = 0
): Int {
    val value by animateIntAsState(
        targetValue = target,
        animationSpec = tween(1500),
        label = "animateInt"
    )
    return value
}