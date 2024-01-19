package com.example.controlpivot.ui.screen.createpivot

import androidx.annotation.DrawableRes
import com.example.controlpivot.R
import com.example.controlpivot.ui.screen.Screen


sealed class BottomNavScreen(
    val route: String,
    @DrawableRes val activeIcon: Int,
    @DrawableRes val inactiveIcon: Int,
) {
    object PivotMachines : BottomNavScreen(
        Screen.PivotMachines.route,
        R.drawable.writing_filled,
        R.drawable.writing_outlined
    )

    object Climate : BottomNavScreen(
        Screen.Climate.route,
        R.drawable.cloudy_day,
        R.drawable.cloudy_day_outlined
    )

    object Control : BottomNavScreen(
        Screen.Control.route,
        R.drawable.control_filled,
        R.drawable.control_outlined
    )

    object Settings : BottomNavScreen(
        Screen.Session.route,
        R.drawable.user_filled,
        R.drawable.user_outlined
    )
}