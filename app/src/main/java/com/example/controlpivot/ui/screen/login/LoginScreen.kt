package com.example.controlpivot.ui.screen.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.controlpivot.R
import com.example.controlpivot.ui.component.OutlinedTextFieldWithValidation
import com.example.controlpivot.ui.component.PasswordTextField
import com.example.controlpivot.ui.theme.GreenHigh

@Composable
fun LoginScreen(
    state: LoginState,
    onEvent: (LoginEvent) -> Unit,
) {

    val loadingComposition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.loading_green)
    )
    val loadingProgress by animateLottieCompositionAsState(
        composition = loadingComposition,
        restartOnPlay = true,
        iterations = Int.MAX_VALUE
    )

    Column(
        modifier = Modifier
            .wrapContentSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Image(
            painter = painterResource(
                id = R.drawable.irrigation_system
            ),
            contentDescription = null,
            modifier = Modifier
                .size(120.dp)
                .padding(4.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = stringResource(id = R.string.pivot_control),
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Black,
                color = GreenHigh
            ),
        )
        val userHint = stringResource(id = R.string.user_hint)
        var user by remember {
            mutableStateOf("")
        }
        Spacer(modifier = Modifier.height(50.dp))
        OutlinedTextFieldWithValidation(
            value = user,
            onValueChange = { user = it },
            textStyle = if (user == userHint) LocalTextStyle.current.copy(
                color = Color.DarkGray.copy(alpha = 0.4f)
            ) else LocalTextStyle.current,
            keyboardType = KeyboardType.Text,
            label = "Usuario",
            leadingIcon = R.drawable.profile
        )

        Spacer(modifier = Modifier.height(20.dp))

        val passHint = stringResource(id = R.string.password_hint)
        var pass by remember {
            mutableStateOf("")
        }
        PasswordTextField(
            value = pass,
            onValueChange = { pass = it },
            textStyle = if (user == userHint) LocalTextStyle.current.copy(
                color = Color.DarkGray.copy(alpha = 0.4f)
            ) else LocalTextStyle.current,
            keyboardType = KeyboardType.Password,
            label = "Contraseña",
            leadingIcon = R.drawable.lock,
            imeAction = ImeAction.Done,
        )

        Spacer(modifier = Modifier.height(30.dp))

        Box(modifier = Modifier
            .requiredHeight(60.dp)
            .fillMaxWidth(.8f)
            .clip(RoundedCornerShape(25.dp))
            .background(GreenHigh)
            .clickable {
                if (state.isLoading) return@clickable
                onEvent(
                    LoginEvent.Login(
                        Credentials(
                            user.let { it.trim() },
                            pass.let { it.trim() }
                        )
                    )
                )
            }
        ) {
            Row(
                verticalAlignment = CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Spacer(modifier = Modifier.width(20.dp))
                Text(
                    text = stringResource(id = R.string.login),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.White,
                    ),
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    modifier = Modifier
                        .width(40.dp)
                        .height(35.dp)
                        .padding(
                            start = 3.dp,
                            end = 8.dp
                        ),
                    tint = Color.White
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        AnimatedVisibility(
            visible = state.isLoading,
            enter = scaleIn(),
            exit = scaleOut()
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                LottieAnimation(
                    composition = loadingComposition,
                    progress = loadingProgress,
                    modifier = Modifier
                        .size(160.dp)
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(state = LoginState(), onEvent = { LoginEvent.Login(Credentials()) })
}