package com.example.controlpivot.ui.component

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.example.controlpivot.ui.model.IdPivotModel

@Composable
fun PivotSpinner(
    optionList: List<IdPivotModel>,
    selectedOption: String,
    onSelected: (Int) -> Unit,
    loading: () -> Unit,
) {

    val context = LocalContext.current

    var expandedState by remember {
        mutableStateOf(false)
    }
    var option = selectedOption
    var textFieldSize by remember {
        mutableStateOf(Size.Zero)
    }
    val icon = if (expandedState)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Column(
        modifier = Modifier
            .padding(10.dp)
    ) {
        TextField(
            value = option,
            onValueChange = { option = it },
            trailingIcon = {
                Icon(
                    imageVector = icon,
                    contentDescription = "",
                    Modifier.clickable { expandedState = !expandedState }
                )
            },
            modifier = Modifier
                .onGloballyPositioned { coordinates ->
                    textFieldSize = coordinates.size.toSize()
                },
            label = {
                Text(
                    text = "Seleccionar Máquina de Pivote",
                    fontSize = if (selectedOption.isNotEmpty()) 10.sp
                    else 18.sp,
                    fontWeight = if (selectedOption.isNotEmpty()) FontWeight.Thin
                    else FontWeight.Bold,
                )
            },
            readOnly = true,
            textStyle = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            )
        )
        DropdownMenu(
            expanded = expandedState,
            onDismissRequest = { expandedState = false },
            modifier = Modifier
                .width(with(LocalDensity.current) {
                    textFieldSize.width.toDp()
                }),
        ) {
            optionList.forEach { idPivotModel ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = idPivotModel.pivotName,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    },
                    onClick = {
                        option = idPivotModel.pivotName
                        Toast.makeText(
                            context,
                            "" + option,
                            Toast.LENGTH_LONG
                        ).show()
                        onSelected(idPivotModel.idPivot)
                        loading()
                    })
            }
        }
    }
}