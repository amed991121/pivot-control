package com.example.controlpivot.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.controlpivot.ui.theme.GreenHigh
import com.example.controlpivot.ui.theme.GreenMedium
import kotlin.math.roundToInt

@Composable
fun SpeedSelector(
    onSpeedChanged: (Float) -> Unit,
) {
    var speed by remember { mutableFloatStateOf(0f) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {

            Text(
                text = "Velocidad del Motor: ",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 6.dp, end = 4.dp)
            )
            Text(
                text = "${speed.roundToInt()} m/s",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        SpeedSlider(
            value = speed,
            onValueChange = {
                speed = it
                onSpeedChanged(it)
            }
        )

    }
}

@Composable
fun SpeedSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
) {
    val thumbSize = 15.dp

    Slider(
        value = value,
        valueRange = 0f..100f,
        steps = 10,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer {
                translationY = -thumbSize.toPx() / 2
            }
            .padding(7.dp),
        colors = SliderDefaults.colors(
            thumbColor = GreenHigh,
            activeTrackColor = GreenMedium,
            inactiveTrackColor = Color.Gray
        )
    )
}

@Preview
@Composable
fun SpeedSelectorPreview() {
    SpeedSelector(onSpeedChanged = {})
}
