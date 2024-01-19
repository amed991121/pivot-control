package com.example.controlpivot.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.controlpivot.R
import com.example.controlpivot.ui.theme.Green
import com.example.controlpivot.ui.theme.GreenHigh
import kotlin.math.absoluteValue
import kotlin.math.atan
import kotlin.math.cos
import kotlin.math.sin


@Composable
fun QuadrantIrrigation(
    onClick: (Int) -> Unit,
    isRunning: Boolean,
    pauseIrrigation: () -> Unit,
    progress: Float,
    sectorStateList: List<Boolean>,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Spacer(modifier = Modifier.height(16.dp))

        QuadrantView(
            progress,
            getSector = {
                onClick(it)
            },
            sectorStateList = sectorStateList)

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { pauseIrrigation() },
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
                    .wrapContentWidth()
            ) {
                Icon(
                    modifier = Modifier
                        .size(30.dp),
                    painter = if (isRunning) painterResource(id = R.drawable.pause)
                    else painterResource(id = R.drawable.play),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    fontSize = 20.sp,
                    text = if (isRunning) "Pausar" else "Iniciar"
                )
            }

        }
    }
}

@Composable
fun QuadrantView(
    progress: Float,
    getSector: (Int) -> Unit,
    sectorStateList: List<Boolean>,
) {
    var center by remember { mutableStateOf(Offset.Zero) }
    val strokeWidth: Dp = 1.dp


    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Canvas(
            modifier = Modifier
                .aspectRatio(1f)
                .onGloballyPositioned { coordinates ->
                    center = Offset(
                        coordinates.size.width / 2f,
                        coordinates.size.height / 2f
                    )
                }
        ) {
            val centerX = size.width / 2
            val centerY = size.height / 2
            val radius = size.width / 2

            drawCircle(
                color = Green,
                radius = radius,
            )

            drawCircle(
                color = Color.Black,
                center = size.center,
                radius = radius,
                style = Stroke(4f)
            )

            drawArc(
                color = GreenHigh,
                startAngle = 0f,
                sweepAngle = progress,
                useCenter = true
            )


            val angleIncrement = 360f / 4
            var currentAngle = 0f
            for (i in 1..4) {
                var startAngle = currentAngle
                var endAngle = currentAngle - angleIncrement

                val endRadians = Math.toRadians(endAngle.absoluteValue.toDouble())
                val endX = centerX + (radius * cos(endRadians)).toFloat()
                val endY = centerY + (radius * sin(endRadians)).toFloat()

                val fillProgress =
                    progress.coerceIn(startAngle.absoluteValue, endAngle.absoluteValue)

                drawLine(
                    color = Color.Black,
                    start = Offset(centerX, centerY),
                    end = Offset(endX, endY),
                    strokeWidth = strokeWidth.toPx()
                )


                drawLine(
                    color = Color.Black,
                    start = Offset(centerX, centerY),
                    end = calculateEndPoint(centerX, centerY, radius, fillProgress),
                    strokeWidth = strokeWidth.toPx()
                )

                currentAngle = endAngle
            }
        }

        Row(
            modifier = Modifier.align(Alignment.Center)
        ) {
            Box(
                modifier = Modifier
                    .aspectRatio(1f)
                    .fillMaxSize()
                    .padding(20.dp)
            ) {

                Box(
                    modifier = Modifier
                        .size(170.dp)
                        .align(Alignment.BottomEnd)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            getSector(1)
                        }
                ) {
                    Row(
                        modifier = Modifier
                            .padding(18.dp)
                            .wrapContentSize()
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                getSector(1)
                            },
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Sector 1",
                            style = TextStyle(
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                            )
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        CircularCheckBox(
                            checked = sectorStateList[0],
                            onCheckedChange = {},
                            borderColor = Color.DarkGray
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .size(170.dp)
                        .align(Alignment.BottomStart)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            getSector(2)
                        }
                ) {
                    Row(
                        modifier = Modifier
                            .padding(18.dp)
                            .wrapContentSize()
                            .align(Alignment.TopEnd)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                getSector(2)
                            },
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Sector 2",
                            style = TextStyle(
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                            )
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        CircularCheckBox(
                            checked = sectorStateList[1],
                            onCheckedChange = {},
                            borderColor = Color.DarkGray
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .size(170.dp)
                        .align(Alignment.TopStart)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            getSector(3)
                        }
                ) {
                    Row(
                        modifier = Modifier
                            .padding(18.dp)
                            .wrapContentSize()
                            .align(Alignment.BottomEnd)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                getSector(3)
                            },
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Sector 3",
                            style = TextStyle(
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                            )
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        CircularCheckBox(
                            checked = sectorStateList[2],
                            onCheckedChange = {},
                            borderColor = Color.DarkGray
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .size(170.dp)
                        .align(Alignment.TopEnd)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            getSector(4)
                        }
                ) {
                    Row(
                        modifier = Modifier
                            .padding(18.dp)
                            .wrapContentSize()
                            .align(Alignment.BottomStart)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                getSector(4)
                            },
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Sector 4",
                            style = TextStyle(
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                            )
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        CircularCheckBox(
                            checked = sectorStateList[3],
                            onCheckedChange = {},
                            borderColor = Color.DarkGray
                        )
                    }
                }
            }

        }
    }
}

private fun calculateEndPoint(
    centerX: Float,
    centerY: Float,
    radius: Float,
    angle: Float,
): Offset {
    val radians = Math.toRadians(angle.toDouble())
    val endX = centerX + (radius * cos(radians)).toFloat()
    val endY = centerY + (radius * sin(radians)).toFloat()
    return Offset(endX, endY)
}

private fun calculateAngle(
    centerX: Float,
    centerY: Float,
    radius: Float,
    angle: Float,
): Float {
    var sectorAngle: Float
    val radians = Math.toRadians(angle.toDouble())
    val endX = centerX + (radius * cos(radians))
    val endY = centerY + (radius * sin(radians))
    sectorAngle = if (endY.toInt() == 664) {
        Math.toDegrees(
            atan((endY - centerY) / (endX - centerX))
        ).toFloat()
    } else {
        Math.toDegrees(
            atan((endX - centerX).absoluteValue / (endY - centerY).absoluteValue)
        ).toFloat()
    }
    return sectorAngle
}


