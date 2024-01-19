package com.example.controlpivot.ui.screen


sealed class Screen(
    val route: String,
) {
    object PivotMachines : Screen("Información") {
        object List : Screen("${route}/list")
        object Machine : Screen("${route}/new")
    }
    object Climate : Screen("Clima")
    object Control : Screen("Control")
    object Session : Screen("Sesión")
}

