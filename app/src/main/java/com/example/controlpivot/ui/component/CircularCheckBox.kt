package com.example.controlpivot.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CheckboxColors
import androidx.compose.material.CheckboxDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.controlpivot.ui.theme.GreenHigh


@Composable
fun CircularCheckBox(
    size: Dp = defaultSize,
    borderStroke: Dp = defaultBorderStroke,
    borderColor: Color = defaultBorderColor,
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    enabled: Boolean = true,
    colors: CheckboxColors = CheckboxDefaults.colors(
        checkedColor = defaultCheckedColor,
        checkmarkColor = defaultCheckMarkColor,
    )
) {
    CustomCheckBox(
        checked = checked,
        onCheckedChange = onCheckedChange,
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .border(
                border = BorderStroke(
                    width = if (checked) 0.dp else borderStroke,
                    color = borderColor
                ), CircleShape
            ),
        enabled = enabled,
        colors = colors
    )
}

private val defaultSize = 25.dp
private val defaultBorderStroke = 2.dp
private val defaultBorderColor = Color.LightGray
private val defaultCheckedColor = GreenHigh.copy(alpha = 0.7f)
private val defaultCheckMarkColor = Color.White

@Preview
@Composable
fun CircularCheckBoxPreview() {
    CircularCheckBox(checked = true, onCheckedChange = {})
}