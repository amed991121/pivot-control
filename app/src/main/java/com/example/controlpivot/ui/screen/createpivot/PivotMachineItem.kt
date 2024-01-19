package com.example.controlpivot.ui.screen.createpivot

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.TooltipState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.Thin
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.controlpivot.R
import com.example.controlpivot.data.local.model.PivotMachineEntity
import com.example.controlpivot.ui.theme.Green
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PivotMachineItem(
    machine: PivotMachineEntity,
    cornerRadius: Dp = 10.dp,
    onDeleteClick: () -> Unit,
    onClick: () -> Unit,
    heightBox: Boolean,
    onExpanded: () -> Unit,
    tooltipState: TooltipState,
    scope: CoroutineScope,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val item = Path().apply {
                lineTo(size.width, 0f)
                lineTo(size.width, size.height)
                lineTo(0f, size.height)
                close()
            }

            clipPath(item) {
                drawRoundRect(
                    color = Green,
                    size = size,
                    cornerRadius = CornerRadius(cornerRadius.toPx())
                )
            }
        }

        Row {

            Image(
                painter = painterResource(
                    id = R.drawable.irrigation_machine
                ),
                contentDescription = null,
                modifier = Modifier
                    .size(60.dp)
                    .padding(12.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 5.dp, bottom = 32.dp, end = 16.dp)
            ) {
                Text(
                    text = machine.name,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = Bold,
                    fontSize = 22.sp,
                    color = colorScheme.onSurface,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = "Ubicación: ${machine.location}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = Thin,
                    color = colorScheme.onSurface,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = "Dotación: ${machine.endowment} L/seg ha",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = Thin,
                    color = colorScheme.onSurface,
                    overflow = TextOverflow.Ellipsis
                )

                if (heightBox) {

                    Text(
                        text = "Caudal: ${machine.flow} L/seg",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = Thin,
                        color = colorScheme.onSurface,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        text = "Presión: ${machine.pressure} kPa",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = Thin,
                        color = colorScheme.onSurface,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        text = "Longitud Total: ${machine.length} m",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = Thin,
                        color = colorScheme.onSurface,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        text = "Área: ${machine.area} m^2",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = Thin,
                        color = colorScheme.onSurface,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        text = "Tiempo de vuelta: ${machine.speed} horas",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = Thin,
                        color = colorScheme.onSurface,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        text = "Potencia: ${machine.power} kW",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = Thin,
                        color = colorScheme.onSurface,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        text = "Eficiencia: ${machine.efficiency} %",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = Thin,
                        color = colorScheme.onSurface,
                        overflow = TextOverflow.Ellipsis,
                    )
                }

            }
        }

        IconButton(
            onClick = onDeleteClick,
            modifier = Modifier.align(Alignment.TopEnd)
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete alarm",
                modifier = Modifier.size(25.dp)
            )

        }

        IconButton(
            onClick = {},
            modifier = Modifier
                .align(Alignment.BottomStart),

        ) {
            TooltipBox(
                positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
                tooltip = {
                    PlainTooltip {
                        Text(
                            text = if (machine.isSave) stringResource(id = R.string.success_registred)
                            else stringResource(id = R.string.pending_register)
                        )
                    }
                },
                state = tooltipState
            ) {
            }
            Icon(
                imageVector = if (machine.isSave) Icons.Default.Done else Icons.Default.Warning,
                contentDescription = "Pending Registration",
                modifier = Modifier
                    .size(18.dp)
                    .padding(top = 5.dp)
                    .clickable { scope.launch { tooltipState.show() } }
            )

        }

        IconButton(
            onClick = {},
            modifier = Modifier
                .align(Alignment.BottomEnd)

        ) {
            Icon(
                imageVector = if (!heightBox) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                contentDescription = "isExpanded",
                modifier = Modifier
                    .size(22.dp)
                    .clickable { onExpanded() }
            )

        }


    }

}



