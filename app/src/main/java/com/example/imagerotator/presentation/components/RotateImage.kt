package com.example.imagerotator.presentation.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.wear.compose.material.MaterialTheme
import com.example.imagerotator.R

@Composable
fun RotateImage(
    modifier: Modifier,
    rotationDegrees: Float = 0f
) {
    Image(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .rotate(rotationDegrees),
        painter = painterResource(id = R.drawable.target_image),
        contentDescription = "targetImage"
    )
}

@Composable
fun RotateAnimation(
    modifier: Modifier,
    changeAmount: Float
) {
    val currentRotation by remember { mutableStateOf(0f) }
    val rotation = remember { Animatable(currentRotation) }

    LaunchedEffect(changeAmount != 0f) {
        rotation.animateTo(
            targetValue = changeAmount + 360f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 1000,
                    easing = LinearEasing
                ),
                repeatMode = RepeatMode.Restart
            )
        )
    }

    RotateImage(modifier = modifier, rotationDegrees = rotation.value)
}
