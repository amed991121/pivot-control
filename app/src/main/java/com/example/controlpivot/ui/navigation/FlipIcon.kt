package com.example.controlpivot.ui.navigation

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource

@Composable
fun  FlipIcon(
    modifier: Modifier = Modifier,
    isActive: Boolean,
    activeIcon: Int,
    inactiveIcon: Int,
    contentDescription: String,
) {
    val animationRotation by animateFloatAsState(
        targetValue = if (isActive) 180f else 0f,
        animationSpec = spring(
            stiffness = Spring.StiffnessLow,
            dampingRatio = Spring.DampingRatioMediumBouncy
        ), label = ""
    )
    Box(
        modifier = modifier
            //.graphicsLayer { rotationY = animationRotation },
        ,contentAlignment = Alignment.Center,
    ) {
        Icon(
            painter = painterResource(id = if (animationRotation > 90f) activeIcon else inactiveIcon),
            contentDescription = contentDescription,
        )
    }
}
