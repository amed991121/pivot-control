package com.example.controlpivot.ui.screen.climate

sealed class ClimateEvent {

    data class SelectClimateByIdPivot(val id: Int) : ClimateEvent()
}