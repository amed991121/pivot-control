package com.example.controlpivot.ui.component

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidthIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Scaffold
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.controlpivot.R
import com.example.controlpivot.convertCharacter
import com.example.controlpivot.ui.theme.GreenHigh

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BottomSheetIrrigation(
    checkIrrigate: Boolean,
    sector: Int,
    dosage: Int,
    onEvent: (AddTagEvent) -> Unit,
) {
    var bottomCheckIrrigate by remember { mutableStateOf(checkIrrigate) }
    var bottomDosage by remember { mutableIntStateOf(dosage) }

    Scaffold(
        modifier = Modifier
            .wrapContentHeight()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Box(
                modifier = Modifier,
                contentAlignment = Alignment.TopCenter
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    Text(
                        modifier = Modifier.padding(bottom = 10.dp),
                        text = "SECTOR $sector",
                        style = MaterialTheme.typography.headlineSmall
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .height(60.dp)
                            .background(
                                color = Color.LightGray.copy(alpha = 0.3f),
                                shape = RoundedCornerShape(6.dp)
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            modifier = Modifier.padding(start = 15.dp),
                            text = "Regar",
                            style = MaterialTheme.typography.headlineSmall
                        )
                        Box(
                            modifier = Modifier.padding(end = 75.dp)
                        ) {
                            CircularCheckBox(
                                checked = bottomCheckIrrigate,
                                onCheckedChange = { bottomCheckIrrigate = !bottomCheckIrrigate },
                                borderColor = Color.DarkGray
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .height(60.dp)
                            .background(
                                color = Color.LightGray.copy(alpha = 0.3f),
                                shape = RoundedCornerShape(6.dp)
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            modifier = Modifier.padding(start = 15.dp),
                            text = "Dosificación",
                            style = MaterialTheme.typography.headlineSmall
                        )
                        Row(
                            modifier = Modifier.padding(end = 15.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(35.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(Color.LightGray.copy(alpha = 0.6f))
                                    .padding(4.dp)
                                    .clickable(
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = null
                                    ) {
                                        if (bottomDosage > 0)
                                            bottomDosage = bottomDosage.dec()
                                    }
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.minus),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(25.dp)
                                        .padding(2.dp)
                                        .align(Alignment.Center),
                                    tint = Color.Black.copy(alpha = 0.5f)
                                )
                            }
                            BasicTextField(
                                value = bottomDosage.toString(),
                                onValueChange = {
                                    it.convertCharacter()
                                    bottomDosage = if (it.isEmpty()) 0 else it.toInt()
                                },
                                modifier = Modifier
                                    .requiredWidthIn(min = 20.dp, max = 100.dp)
                                    .width(35.dp)
                                    .height(30.dp)
                                    .background(Color.Transparent)
                                    .border(0.dp, Color.Transparent)
                                    .align(Alignment.CenterVertically)
                                    .padding(start = 4.dp, end = 4.dp),
                                textStyle = MaterialTheme.typography.titleLarge.copy(
                                    textAlign = TextAlign.Center
                                ),
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    keyboardType = KeyboardType.Number,
                                    imeAction = ImeAction.Done
                                ),
                            )
                            Box(
                                modifier = Modifier
                                    .size(35.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(Color.LightGray.copy(alpha = 0.6f))
                                    .padding(4.dp)
                                    .clickable(
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = null
                                    ) {
                                        bottomDosage = bottomDosage.inc()
                                    }
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.plus),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(25.dp)
                                        .padding(2.dp)
                                        .align(Alignment.Center),
                                    tint = Color.Black.copy(alpha = 0.5f)
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(15.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .height(110.dp)
                            .background(
                                color = Color.LightGray.copy(alpha = 0.3f),
                                shape = RoundedCornerShape(6.dp)
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        SpeedSelector(onSpeedChanged = {})
                    }

                }

            }
            Spacer(modifier = Modifier.height(15.dp))
            TextButton(
                enabled = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                onClick = {
                    onEvent(
                        AddTagEvent.Save(
                            bottomCheckIrrigate, bottomDosage
                        )
                    )
                },
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = GreenHigh,
                    contentColor = Color.White
                )
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(5.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    text = "Aceptar"
                )
            }
        }
    }

}

sealed class AddTagEvent() {
    class Save(val checkIrrigate: Boolean, val dosage: Int) : AddTagEvent()
    object Close : AddTagEvent()
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun SheetPreview() {
    BottomSheetIrrigation(checkIrrigate = true, sector = 1, dosage = 0, onEvent = {})
}