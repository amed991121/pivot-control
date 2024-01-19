package com.example.controlpivot.ui.screen.pivotcontrol

import com.example.controlpivot.data.local.model.PivotControlEntity
import com.example.controlpivot.data.local.model.SectorControl
import com.example.controlpivot.ui.model.IdPivotModel
import com.example.controlpivot.ui.model.PivotControlsWithSectors

data class ControlState(
    val controls: PivotControlsWithSectors = PivotControlsWithSectors(
        id = 0,
        idPivot = 0,
        progress = 0f,
        isRunning = false,
        sectorList = listOf(
            SectorControl(
                id = 1,
                sector_id = 0,
                irrigateState = false,
                dosage = 0,
                motorVelocity = 0,
                sector_control_id = 0
            ),
            SectorControl(
                id = 2,
                sector_id = 0,
                irrigateState = false,
                dosage = 0,
                motorVelocity = 0,
                sector_control_id = 0
            ),
            SectorControl(
                id = 3,
                sector_id = 0,
                irrigateState = false,
                dosage = 0,
                motorVelocity = 0,
                sector_control_id = 0
            ),
            SectorControl(
                id = 4,
                sector_id = 0,
                irrigateState = false,
                dosage = 0,
                motorVelocity = 0,
                sector_control_id = 0
            ),
        ),
        stateBomb = false,
        wayToPump = false,
        turnSense = false
    ),
    var selectedIdPivot: Int = 0,
    var idPivotList: List<IdPivotModel> = listOf(),
    var networkStatus : Boolean = false
)
