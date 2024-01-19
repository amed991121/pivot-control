package com.example.controlpivot.ui.screen.createpivot

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.controlpivot.R
import com.example.controlpivot.ui.component.OutlinedTextFieldWithValidation
import com.example.controlpivot.ui.theme.GreenMedium

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddEditPivotMachineScreen(
    state: PivotMachineState,
    onEvent: (PivotMachineEvent) -> Unit,
    navEvent: (ScreensNavEvent) -> Unit,
) {
    val scrollState = rememberScrollState()
    var nameTextValue = state.currentMachine.name

    var locationTextValue = state.currentMachine.location

    var endowmentTextValue = state.currentMachine.endowment

    var flowTextValue = state.currentMachine.flow

    var pressureTextValue = state.currentMachine.pressure

    var lengthTextValue = state.currentMachine.length

    var areaTextValue = state.currentMachine.area

    var powerTextValue = state.currentMachine.power

    var speedTextValue = state.currentMachine.speed

    var efficiencyTextValue = state.currentMachine.efficiency

    Scaffold(
        modifier = Modifier.padding(bottom = 75.dp),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onEvent(
                        PivotMachineEvent.CreateMachine(
                            state.currentMachine.copy(
                                name = nameTextValue,
                                location = locationTextValue,
                                endowment = endowmentTextValue,
                                flow = flowTextValue,
                                pressure = pressureTextValue,
                                length = lengthTextValue,
                                area = areaTextValue,
                                power = powerTextValue,
                                speed = speedTextValue,
                                efficiency = efficiencyTextValue,
                                isSave = false,
                            )
                        )
                    )
                },
                shape = FloatingActionButtonDefaults.extendedFabShape,
                containerColor = GreenMedium,
                contentColor = Color.Black,
                modifier = Modifier
            ) {
                Icon(imageVector = Icons.Default.Done, contentDescription = "Save pivot machine")
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(state = scrollState)
                .padding(start = 15.dp, end = 15.dp)
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .clip(RoundedCornerShape(15.dp))
                        .background(Color.LightGray.copy(alpha = 0.2f))
                        .clickable { navEvent(ScreensNavEvent.Back) },

                    ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                        contentDescription = null,
                        modifier = Modifier
                            .size(40.dp)
                            .padding(7.dp),
                        tint = Color.Gray,
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = stringResource(id = R.string.new_pivot_machine),
                        style = MaterialTheme.typography.h5.copy(fontFamily = FontFamily.SansSerif),
                        modifier = Modifier
                            .padding(start = 5.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            HorizontalDivider(
                modifier = Modifier.height(5.dp),
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextFieldWithValidation(
                label = "Nombre",
                value = nameTextValue,
                keyboardType = KeyboardType.Text,
                leadingIcon = R.drawable.ic_name,
                onValueChange = { nameTextValue = it.toString() }
            )

            OutlinedTextFieldWithValidation(
                label = "Ubicación",
                value = locationTextValue,
                keyboardType = KeyboardType.Text,
                leadingIcon = R.drawable.ic_location,
                onValueChange = { locationTextValue = it.toString() },
                singleLine = false,
            )

            OutlinedTextFieldWithValidation(
                label = "Dotación",
                value = endowmentTextValue,
                keyboardType = KeyboardType.Decimal,
                leadingIcon = R.drawable.ic_flow,
                suffix = "L/seg ha",
                onValueChange = { endowmentTextValue = it.toDouble() }

            )

            OutlinedTextFieldWithValidation(
                label = "Caudal Nominal",
                value = flowTextValue,
                keyboardType = KeyboardType.Decimal,
                leadingIcon = R.drawable.ic_endowment,
                suffix = "L/seg",
                onValueChange = { flowTextValue = it.toDouble() }
            )

            OutlinedTextFieldWithValidation(
                label = "Presión",
                value = pressureTextValue,
                keyboardType = KeyboardType.Decimal,
                leadingIcon = R.drawable.ic_pressure,
                suffix = "kPa",
                onValueChange = { pressureTextValue = it.toDouble() }
            )

            OutlinedTextFieldWithValidation(
                label = "Longitud Total",
                value = lengthTextValue,
                keyboardType = KeyboardType.Decimal,
                leadingIcon = R.drawable.ic_length,
                suffix = "m",
                onValueChange = { lengthTextValue = it.toDouble() }
            )

            OutlinedTextFieldWithValidation(
                label = "Área",
                value = areaTextValue,
                keyboardType = KeyboardType.Decimal,
                leadingIcon = R.drawable.ic_area,
                suffix = "m^2",
                onValueChange = { areaTextValue = it.toDouble() }
            )

            OutlinedTextFieldWithValidation(
                label = "Potencia",
                value = powerTextValue,
                keyboardType = KeyboardType.Decimal,
                leadingIcon = R.drawable.ic_power,
                suffix = "kW",
                onValueChange = { powerTextValue = it.toDouble() }
            )

            OutlinedTextFieldWithValidation(
                label = "Velocidad del Motor",
                value = speedTextValue,
                keyboardType = KeyboardType.Decimal,
                leadingIcon = R.drawable.ic_speed2,
                suffix = "rpm",
                onValueChange = { speedTextValue = it.toDouble() }
            )

            OutlinedTextFieldWithValidation(
                label = "Eficiencia",
                value = efficiencyTextValue,
                keyboardType = KeyboardType.Decimal,
                leadingIcon = R.drawable.ic_efficiency,
                suffix = "%",
                onValueChange = { efficiencyTextValue = it.toDouble() },
                imeAction = ImeAction.Done
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}



