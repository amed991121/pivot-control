package com.example.controlpivot.ui.navigation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.controlpivot.ui.screen.createpivot.BottomNavScreen

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun BottomNavBarAnimation(
    navController: NavController,
) {
    val screens = listOf(
        BottomNavScreen.PivotMachines,
        BottomNavScreen.Climate,
        BottomNavScreen.Control,
        BottomNavScreen.Settings
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    ElevatedCard(
        Modifier
            .height(75.dp),
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row(
            Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            screens.forEach { screen ->
                val isSelected = currentRoute?.contains(screen.route) ?: false
                val animatedWeight by animateFloatAsState(
                    targetValue = if (isSelected) 1.5f else .5f, label = ""
                )
                Box(
                    modifier = Modifier.weight(animatedWeight),
                    contentAlignment = Alignment.Center,
                ) {
                    val interactionSource = remember { MutableInteractionSource() }
                    BottomNavItem(
                        modifier = Modifier.clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        screen = screen,
                        isSelected = isSelected
                    )
                }
            }
        }
    }

}