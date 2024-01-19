package com.example.controlpivot.ui.screen.pivotcontrol

import com.example.controlpivot.data.local.model.PivotControlEntity
import com.example.controlpivot.data.local.model.SectorControl

sealed class ControlEvent {

    data class SaveControls(val controls: PivotControlEntity) :
        ControlEvent()
    data class SaveSectors(val sectors: SectorControl) : ControlEvent()
    data class SelectControlByIdPivot(val id: Int) : ControlEvent()
    data class ShowMessage(val string: Int) : ControlEvent()

}