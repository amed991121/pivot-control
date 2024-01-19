package com.example.controlpivot.ui.screen.createpivot

import com.example.controlpivot.data.local.model.PivotMachineEntity

data class PivotMachineState(
    val machines: List<PivotMachineEntity> = listOf(),
    var currentMachine: PivotMachineEntity = PivotMachineEntity(),
    var isLoading: Boolean = false
)
