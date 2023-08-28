/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package com.example.imagerotator.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.rotary.onRotaryScrollEvent
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.example.imagerotator.presentation.components.RotateAnimation
import com.example.imagerotator.presentation.viewmodel.RotaryScrollViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: RotaryScrollViewModel by viewModels()

        setContent {
            AppScreen(viewModel = viewModel)
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AppScreen(modifier: Modifier = Modifier, viewModel: RotaryScrollViewModel) {
    val focusRequester: FocusRequester = remember { FocusRequester() }
    val coroutineScope = rememberCoroutineScope()

    RotateAnimation(
        modifier = modifier
            .fillMaxSize()
            .onRotaryScrollEvent {
                coroutineScope.launch {
                    viewModel.onRotaryChangeSpeed(it.verticalScrollPixels)
                }
                true
            }
            .focusRequester(focusRequester)
            .focusable(),
        speedLevel = viewModel.currentSpeed.collectAsState().value
    )

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}

@Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true)
@Composable
fun DefaultPreview() {
    AppScreen(viewModel = RotaryScrollViewModel())
}
