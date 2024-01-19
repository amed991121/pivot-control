package com.example.controlpivot.ui.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.example.controlpivot.ui.screen.createpivot.BottomNavScreen
import com.example.controlpivot.ui.theme.GreenLow

@Composable
fun BottomNavItem(
    modifier: Modifier = Modifier,
    screen: BottomNavScreen,
    isSelected: Boolean,
) {

    val animatedHeight by animateDpAsState(
        targetValue = if (isSelected) 50.dp else 40.dp,
        label = ""
    )
    val animatedElevation by animateDpAsState(
        targetValue = if (isSelected) 15.dp else 0.dp,
        label = ""
    )
    val animatedAlpha by animateFloatAsState(
        targetValue = if (isSelected) 1f else .5f, label = ""
    )
    val animatedIconSize by animateDpAsState(
        targetValue = if (isSelected) 28.dp else 28.dp,
        animationSpec = spring(
            stiffness = Spring.StiffnessLow,
            dampingRatio = Spring.DampingRatioMediumBouncy
        ), label = ""
    )
    val animatedColor by animateColorAsState(
        targetValue = if (isSelected) GreenLow
        else MaterialTheme.colorScheme.background, label = ""
    )

    Box(
        modifier = modifier
        .background(color = MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            modifier = Modifier
                .height(animatedHeight)
                .shadow(
                    elevation = 0.dp,
                    shape = RoundedCornerShape(20.dp)
                )
                .background(
                    color = animatedColor,
                    shape = RoundedCornerShape(20.dp)
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            FlipIcon(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .fillMaxHeight()
                    .padding(start = 11.dp)
                    .alpha(animatedAlpha)
                    .size(animatedIconSize),
                isActive = isSelected,
                activeIcon = screen.activeIcon,
                inactiveIcon = screen.inactiveIcon,
                contentDescription = screen.route
            )

            AnimatedVisibility(visible = isSelected) {
                Text(
                    text = screen.route,
                    modifier = Modifier.padding(start = 8.dp, end = 15.dp),
                    maxLines = 1,
                )
            }
        }
    }
}