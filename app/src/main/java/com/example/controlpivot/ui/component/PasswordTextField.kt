package com.example.controlpivot.ui.component


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.controlpivot.R
import com.example.controlpivot.convertCharacter
import com.example.controlpivot.ui.theme.GreenHigh
import com.example.controlpivot.ui.theme.GreenLow
import com.example.controlpivot.ui.theme.PurpleGrey40

@Composable
fun PasswordTextField(
    label: String,
    value: Any,
    keyboardType: KeyboardType,
    leadingIcon: Int,
    trailingIcon: @Composable()(() -> Unit)? = null,
    singleLine: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = TextStyle.Default,
    suffix: String = "",
    visualTransformation: VisualTransformation = VisualTransformation.None,
    imeAction: ImeAction = ImeAction.Next,
    onValueChange: (String) -> Unit,
) {
    var text by remember { mutableStateOf(if (value == 0.0) "" else value.toString()) }
    var isError by remember { mutableStateOf(false) }
    var isFocused by remember { mutableStateOf(false) }
    var passwordHidden by rememberSaveable { mutableStateOf(true) }
    var isTextFieldFilled by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val errorMessage = "Campo vacío"

    OutlinedTextField(
        value = text,
        onValueChange = {
            text = it
            onValueChange(
                if(value is Double && text.isEmpty()) "0.0" else text
            )
            isError = it.isEmpty()
            isTextFieldFilled = it.isNotEmpty()
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 8.dp)
            .onFocusChanged {
                isFocused = it.isFocused
                if (isTextFieldFilled) {
                    //focusManager.clearFocus()
                }
            },

        label = {
            Text(
                text = if (isError) "${label}*" else label,
                fontSize = if (isFocused || text.isNotEmpty() || value is Long) 12.sp
                else 15.sp,
                color = when {
                    isFocused && !isError -> GreenHigh
                    !isFocused && text.isNotEmpty() -> GreenHigh
                    isError -> Color.Red
                    else -> Color.Black
                }
            )
        },
        singleLine = singleLine,
        readOnly = readOnly,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = if (text.isEmpty()) Color.Black else GreenHigh,
            errorBorderColor = Color.Red,
            focusedLabelColor = GreenHigh,
            unfocusedContainerColor = if (text.isEmpty()) GreenLow else Color.Transparent
        ),
        leadingIcon = {
            Icon(
                modifier = Modifier
                    .size(24.dp),
                painter = painterResource(id = leadingIcon),
                contentDescription = label,
                tint = PurpleGrey40
            )
        },
        trailingIcon = {
            IconButton(onClick = { passwordHidden = !passwordHidden }) {
                val visibilityIcon =
                    if (passwordHidden) R.drawable.show else R.drawable.hide
                Icon(
                    painter = painterResource(id = visibilityIcon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp),
                    tint = PurpleGrey40
                )
            }
        },
        isError = isError,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = imeAction,
            keyboardType = keyboardType
        ),
        visualTransformation = if (passwordHidden) PasswordVisualTransformation()
        else VisualTransformation.None,
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
            }
        ),
        supportingText = {
            Text(
                text = if (isError) errorMessage else "",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End,
                color = Color.Red,
                fontSize = 10.sp,
            )
        },
        suffix = { Text(text = suffix) },
        shape = RoundedCornerShape(25.dp),
        textStyle = textStyle

    )
}

