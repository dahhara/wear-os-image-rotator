package com.example.imagerotator.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.math.max
import kotlin.math.min

class RotaryScrollViewModel : ViewModel() {
    val currentSpeed: MutableStateFlow<Int> = MutableStateFlow(0)
    fun onRotaryChangeSpeed(pixels: Float) {
        currentSpeed.value = when {
            pixels <= ROTARY_PLAY && pixels >= -ROTARY_PLAY -> {
             // ignore small change by rotary input
                currentSpeed.value
            }
            pixels > ROTARY_PLAY -> {
                val positiveSpeed = min(currentSpeed.value + SPEED_STEP, MAX_SPEED)
                if (positiveSpeed < 0) 0 else positiveSpeed
            }
            pixels < -ROTARY_PLAY -> {
                val negativeSpeed = max(currentSpeed.value - SPEED_STEP, MIN_SPEED)
                if (negativeSpeed > 0) 0 else negativeSpeed
            }
            else -> currentSpeed.value
        }
    }

    companion object {
        private const val SPEED_STEP = 2
        private const val ROTARY_PLAY = 20
        private const val MAX_SPEED: Int = 100
        private const val MIN_SPEED: Int = -100
    }
}
