@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.controlpivot.ui.screen.session

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.controlpivot.R
import com.example.controlpivot.ui.component.OutlinedTextFieldWithValidation
import com.example.controlpivot.ui.component.PasswordTextField
import com.example.controlpivot.ui.screen.login.LoginEvent
import com.example.controlpivot.ui.screen.login.LoginState
import com.example.controlpivot.ui.theme.GreenHigh
import com.example.controlpivot.ui.theme.GreenLow
import com.example.controlpivot.ui.theme.GreenMedium

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SessionScreen(
    onClearSession: () -> Unit,
    state: LoginState?,
    onEvent: (LoginEvent) -> Unit,
) {
    var openDialog by remember { mutableStateOf(false) }
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 75.dp),
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .drawBehind {
                        translate(top = -650f) {
                            drawCircle(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        GreenLow,
                                        GreenMedium,
                                        GreenHigh
                                    )
                                ),
                                radius = 300.dp.toPx(),
                            )
                        }
                    }
            ) {}

            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(top = 95.dp, start = 20.dp, end = 20.dp, bottom = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .background(color = Color.Transparent, shape = CircleShape)
                        .padding(bottom = 12.dp),
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.person1),
                        contentDescription = null,
                        modifier = Modifier
                            .size(120.dp)
                            .align(Center)
                    )
                }
                Text(
                    text = state?.session?.name ?: "",
                    fontSize = 25.sp,
                    fontFamily = FontFamily.Serif
                )
                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextFieldWithValidation(
                    label = stringResource(id = R.string.user_hint),
                    value = state?.session?.userName ?: "",
                    textStyle = LocalTextStyle.current,
                    keyboardType = KeyboardType.Text,
                    leadingIcon = R.drawable.profile,
                    onValueChange = {},
                    readOnly = true
                )

                Spacer(modifier = Modifier.height(10.dp))

                PasswordTextField(
                    label = stringResource(id = R.string.password_hint),
                    value = state?.session?.password ?: "",
                    textStyle = LocalTextStyle.current,
                    keyboardType = KeyboardType.Password,
                    leadingIcon = R.drawable.lock,
                    onValueChange = {},
                    readOnly = true,

                    )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextFieldWithValidation(
                    label = stringResource(id = R.string.role),
                    value = state?.session?.role ?: "",
                    textStyle = LocalTextStyle.current,
                    keyboardType = KeyboardType.Text,
                    leadingIcon = R.drawable.category,
                    onValueChange = {},
                    readOnly = true
                )

                Spacer(modifier = Modifier.height(20.dp))

                Box(modifier = Modifier
                    .requiredHeight(60.dp)
                    .fillMaxWidth(0.8f)
                    .clip(RoundedCornerShape(25.dp))
                    .background(GreenMedium)
                    .clickable {
                        openDialog = true
                    }
                ) {
                    Row(
                        verticalAlignment = CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Spacer(modifier = Modifier.width(20.dp))
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = "Close Session",
                            modifier = Modifier
                                .width(40.dp)
                                .height(35.dp)
                                .padding(
                                    start = 3.dp,
                                    end = 8.dp
                                ),
                            tint = Color.Black
                        )
                        Text(
                            text = stringResource(id = R.string.close_session),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = Color.Black,
                            ),
                            textAlign = TextAlign.Center,
                            fontSize = 18.sp,
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.width(30.dp))
                    }
                }
            }
        }
    }
    if (openDialog) {
        AlertDialog(
            onDismissRequest = { openDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    openDialog = false
                    onClearSession()
                }) {
                    Text(
                        text = "SI",
                        style = TextStyle(
                            fontSize = 15.sp
                        )
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = { openDialog = false }) {
                    Text(
                        text = "NO",
                        style = TextStyle(
                            fontSize = 15.sp
                        )
                    )
                }
            },
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                        contentDescription = null,
                        tint = Color.Black
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Cerrar Sesión",
                        textAlign = TextAlign.Center
                    )
                }
            },
            text = {

                    Text(
                        text = "¿Estás seguro que deseas cerrar sesión?",
                    )
            }
        )
    }
}

