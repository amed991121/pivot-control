package com.example.controlpivot.ui.screen.createpivot

import com.example.controlpivot.data.local.model.PivotMachineEntity

sealed class PivotMachineEvent {

    data class SearchMachine(val query: String) : PivotMachineEvent()
    data class DeleteMachine(val machineId: Int) : PivotMachineEvent()
    data class CreateMachine(val machine: PivotMachineEntity) : PivotMachineEvent()
    data class SelectMachine(val id: Int) : PivotMachineEvent()
    object ResetDataMachine : PivotMachineEvent()
}