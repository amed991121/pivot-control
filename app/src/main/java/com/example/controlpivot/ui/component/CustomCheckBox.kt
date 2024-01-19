package com.example.controlpivot.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.triStateToggleable
import androidx.compose.material.CheckboxColors
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CustomCheckBox(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    colors: CheckboxColors = CheckboxDefaults.colors(),
) {
    val selectableModifier =
        if (onCheckedChange != null) {
            Modifier.triStateToggleable(
                state = ToggleableState(checked),
                onClick = { onCheckedChange(!checked) },
                enabled = enabled,
                interactionSource = interactionSource,
                indication = null
            )
        } else {
            Modifier
        }
    Box(
        modifier = modifier
            .then(selectableModifier)
            .background(
                if (checked) colors.boxColor(
                    enabled = enabled,
                    state = ToggleableState.On
                ).value else colors.boxColor(
                    enabled = enabled,
                    state = ToggleableState.Off
                ).value
            )
    ) {
        if (checked)
            Icon(
                imageVector = Icons.Rounded.Check,
                contentDescription = null,
                tint = colors.checkmarkColor(
                    state = ToggleableState.On
                ).value,
                modifier = Modifier
                    .padding(defaultCheckPadding)
                    .fillMaxSize()
            )
    }
}

private val defaultCheckPadding = 4.dp

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun CustomCheckBoxPreview() {
    CircularCheckBox(checked = true, onCheckedChange = {})
}

