package com.example.imagerotator.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.math.max
import kotlin.math.min

class RotaryScrollViewModel : ViewModel() {
    val currentSpeed: MutableStateFlow<Int> = MutableStateFlow(0)
    fun onRotaryChangeSpeed(pixels: Float) {
        currentSpeed.value = when {
            pixels > 0 -> min(currentSpeed.value + 1, MAX_SPEED)
            pixels < 0 -> max(currentSpeed.value - 1, MIN_SPEED)
            else -> currentSpeed.value
        }
    }

    companion object {
        private const val MAX_SPEED: Int = 10
        private const val MIN_SPEED: Int = -10
    }
}
