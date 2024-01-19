package com.example.controlpivot.ui.screen.createpivot

import android.app.Activity
import androidx.navigation.NavController
import com.example.controlpivot.ui.screen.Screen

sealed class ScreensNavEvent {
    object ToNewPivotMachine : ScreensNavEvent()
    object ToPivotMachineList : ScreensNavEvent()
    object ToClimate : ScreensNavEvent()
    object ToPivotControl : ScreensNavEvent()
    object ToSession : ScreensNavEvent()
    object Back : ScreensNavEvent()
}

fun Activity.pivotMachineNavEvent(
    navController: NavController,
    navEvent: ScreensNavEvent,
) {
    when (navEvent) {
        ScreensNavEvent.Back -> {
            navController.popBackStack()
        }
        ScreensNavEvent.ToNewPivotMachine -> {
            navController.navigate(Screen.PivotMachines.Machine.route)
        }

        ScreensNavEvent.ToPivotMachineList -> {
            navController.navigate(Screen.PivotMachines.List.route)
        }

        ScreensNavEvent.ToClimate -> {
            navController.navigate(Screen.Climate.route)
        }
        ScreensNavEvent.ToPivotControl -> {
            navController.navigate(Screen.Control.route)
        }
        ScreensNavEvent.ToSession -> {
            navController.navigate(Screen.Session.route)
        }
    }
}