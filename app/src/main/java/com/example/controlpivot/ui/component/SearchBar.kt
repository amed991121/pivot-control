package com.example.controlpivot.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.controlpivot.ui.theme.GreenHigh

@Composable
fun SearchBar(
    hint: String = "",
    textStyle: TextStyle = LocalTextStyle.current,
    onValueChange: (String) -> Unit,
) {

    var text by rememberSaveable { mutableStateOf(hint) }

    CustomTextField(
        value = text,
        onValueChange = {
            text = it.changeValueBehavior(hint, text)
            onValueChange(if (text != hint) text else "")
        },
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(align = Alignment.Top)
            .clip(RoundedCornerShape(20.dp))
            .background(color = Color.LightGray.copy(alpha = 0.25f))
            .padding(3.dp),
        textStyle = if (text == hint)
            textStyle.copy(color = GreenHigh.copy(alpha = 0.4f)) else textStyle,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .padding(10.dp),
                tint = GreenHigh.copy(alpha = 0.4f)
            )
        },
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = null,
                modifier = Modifier
                    .size(38.dp)
                    .padding(10.dp)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() })
                    {
                        onValueChange("")
                        text = hint
                    },
                tint = GreenHigh.copy(alpha = 0.4f)
            )

        },
        singleLine = true
    )

}

private fun String.changeValueBehavior(
    hint: String,
    text: String,
    maxLength: Long = Long.MAX_VALUE
): String {
    if (this.length > maxLength) return text
    if (this.isEmpty()) return hint
    if (text == hint) {
        var tempText = this
        hint.forEach { c ->
            tempText = tempText.replaceFirst(c.toString(), "")
        }
        return tempText
    }
    return this
}