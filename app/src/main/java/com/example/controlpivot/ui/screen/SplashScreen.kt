package com.example.controlpivot.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.controlpivot.R

@Composable
fun SplashScreen() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.anim_irrigation))
        val secondComposition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.splash_animation))
        val progress by animateLottieCompositionAsState(
            composition = composition,
        )
        //if (progress == 1.0f) animationEnd(true)
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LottieAnimation(
                    composition = composition,
                    progress = progress,
                    modifier = Modifier
                        .requiredHeight(350.dp)
                        .fillMaxWidth(0.7f),
                )
                LottieAnimation(
                    composition = secondComposition,
                    modifier = Modifier
                        .requiredHeight(350.dp)
                        .fillMaxWidth(0.7f),
                    restartOnPlay = true,
                    iterations = Int.MAX_VALUE
                )
            }
        }
    }
}